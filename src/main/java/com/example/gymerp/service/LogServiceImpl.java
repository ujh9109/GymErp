package com.example.gymerp.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gymerp.dto.PtLogDto;
import com.example.gymerp.dto.VoucherLogDto;
import com.example.gymerp.repository.LogDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private final LogDao logDao;

    // 회원권 유효 여부 확인
    @Override
    public boolean isVoucherValid(long memNum) {
        return logDao.checkVoucherValid(memNum) > 0;
    }

    // 회원권 만료 여부 확인
    @Override
    public boolean isVoucherExpired(long memNum) {
        return logDao.checkVoucherExpired(memNum) > 0;
    }

    // 회원권 단건 조회
    @Override
    public VoucherLogDto getVoucherByMember(long memNum) {
        return logDao.selectVoucherByMember(memNum);
    }

    // 회원권 등록 또는 갱신
    @Override
    @Transactional
    public void saveOrUpdateVoucher(VoucherLogDto dto) {
        VoucherLogDto existing = logDao.selectVoucherByMember(dto.getMemNum());
        if (existing == null) logDao.insertVoucherLog(dto);
        else logDao.updateVoucherLog(dto);
    }

    // 회원권 로그 직접 수정
    @Override
    @Transactional
    public void updateVoucherLogManual(VoucherLogDto dto) {
        logDao.updateVoucherLogManual(dto);
    }

    // 회원권 삭제 또는 회귀 처리
    @Override
    @Transactional
    public void rollbackOrDeleteVoucher(long memNum) {
        logDao.rollbackOrDeleteVoucherLog(memNum);
    }

    // PT 충전 로그 등록
    @Override
    @Transactional
    public void addPtChargeLog(PtLogDto dto) {
        if (dto.getCreatedAt() == null) dto.setCreatedAt(LocalDateTime.now());
        logDao.insertPtChargeLog(dto);
    }

    // PT 소비 로그 등록
    @Override
    @Transactional
    public void addPtConsumeLog(PtLogDto dto) {
        if (dto.getCreatedAt() == null) dto.setCreatedAt(LocalDateTime.now());
        logDao.insertPtConsumeLog(dto);
    }

    // PT 변경 로그 등록
    @Override
    @Transactional
    public void addPtChangeLog(PtLogDto dto) {
        Long memNum = dto.getMemNum();
        Long newEmpNum = dto.getEmpNum();
        String newTrainerName = dto.getTrainerName();

        Long oldEmpNum = logDao.selectOldTrainerEmpNum(memNum);
        String oldTrainerName = logDao.selectOldTrainerName(memNum);

        int totalCount = logDao.selectTotalPtCount(memNum);
        int remainingCount = logDao.selectRemainingPtCount(memNum);
        int totalAmount = logDao.selectTotalPtAmount(memNum);
        int unitPrice = totalCount == 0 ? 0 : totalAmount / totalCount;
        int transferAmount = remainingCount * unitPrice;

        LocalDateTime now = LocalDateTime.now();

        PtLogDto oldLog = PtLogDto.builder()
            .memNum(memNum)
            .empNum(oldEmpNum)
            .trainerName(oldTrainerName)
            .memberName(dto.getMemberName())
            .status("변경")
            .countChange(-remainingCount)
            .totalAmount(-transferAmount)
            .consumeAmount(0)
            .createdAt(now)
            .build();
        logDao.insertPtChangeLog(oldLog);

        PtLogDto newLog = PtLogDto.builder()
            .memNum(memNum)
            .empNum(newEmpNum)
            .trainerName(newTrainerName)
            .memberName(dto.getMemberName())
            .status("변경")
            .countChange(remainingCount)
            .totalAmount(transferAmount)
            .consumeAmount(0)
            .createdAt(now)
            .build();
        logDao.insertPtChangeLog(newLog);
    }

    // PT 전체 환불 로그 등록
    @Override
    @Transactional
    public void addPtFullRefundLog(PtLogDto dto) {
        if (dto.getCreatedAt() == null) dto.setCreatedAt(LocalDateTime.now());
        logDao.insertPtFullRefundLog(dto);
    }

    // PT 로그 직접 수정
    @Override
    @Transactional
    public void updatePtLogManual(PtLogDto dto) {
        logDao.updatePtLogManual(dto);
    }

    // PT 충전 로그 삭제
    @Transactional
    public void deletePtLogBySaleInfo(long memNum, int totalAmount, LocalDateTime createdAt) {
        Map<String, Object> param = new HashMap<>();
        param.put("memNum", memNum);
        param.put("totalAmount", totalAmount);
        param.put("createdAt", createdAt);
        logDao.deletePtLogBySaleInfo(param);
    }

    // 남은 PT 횟수 조회
    @Override
    public int getRemainingPtCount(long memNum) {
        return logDao.selectRemainingPtCount(memNum);
    }
}
