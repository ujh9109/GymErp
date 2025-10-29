package com.example.gymerp.service;

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

    /* 회원권 유효 여부 확인 */
    @Override
    public boolean isVoucherValid(long memNum) {
        return logDao.checkVoucherValid(memNum) > 0;
    }

    /* 회원권 단건 조회 */
    @Override
    public VoucherLogDto getVoucherByMember(long memNum) {
        return logDao.selectVoucherByMember(memNum);
    }

    /* 회원권 등록 또는 연장 */
    @Override
    @Transactional
    public void saveOrUpdateVoucher(VoucherLogDto dto) {
        VoucherLogDto existing = logDao.selectVoucherByMember(dto.getMemNum());
        if (existing == null) logDao.insertVoucherLog(dto);
        else logDao.updateVoucherLog(dto);
    }
    
    /* 회원권 로그 직접 수정 */
    @Override
    @Transactional
    public void updateVoucherLogManual(VoucherLogDto dto) {
        logDao.updateVoucherLogManual(dto);
    }

    /* PT 충전 로그 등록 */
    @Override
    @Transactional
    public void addPtChargeLog(PtLogDto dto) {
        logDao.insertPtChargeLog(dto);
    }

    /* PT 소비 로그 등록 */
    @Override
    @Transactional
    public void addPtConsumeLog(PtLogDto dto) {
        logDao.insertPtConsumeLog(dto);
    }

    /* PT 변경 로그 등록 (기존 트레이너 차감 + 신규 트레이너 추가) */
    @Override
    @Transactional
    public void addPtChangeLog(PtLogDto dto) {
        Long memNum = dto.getMemNum();
        Long newEmpNum = dto.getEmpNum();
        String newTrainerName = dto.getTrainerName();

        // 기존 트레이너 정보 조회
        Long oldEmpNum = logDao.selectOldTrainerEmpNum(memNum);
        String oldTrainerName = logDao.selectOldTrainerName(memNum);

        // 금액 계산
        int totalCount = logDao.selectTotalPtCount(memNum);
        int remainingCount = logDao.selectRemainingPtCount(memNum);
        int totalAmount = logDao.selectTotalPtAmount(memNum);
        int unitPrice = totalCount == 0 ? 0 : totalAmount / totalCount;
        int transferAmount = remainingCount * unitPrice;

        // 기존 트레이너 로그 (-)
        PtLogDto oldLog = PtLogDto.builder()
            .memNum(memNum)
            .empNum(oldEmpNum)
            .trainerName(oldTrainerName)
            .memberName(dto.getMemberName())
            .status("변경")
            .countChange(-remainingCount)
            .totalAmount(-transferAmount)
            .consumeAmount(0)
            .build();
        logDao.insertPtChangeLog(oldLog);

        // 신규 트레이너 로그 (+)
        PtLogDto newLog = PtLogDto.builder()
            .memNum(memNum)
            .empNum(newEmpNum)
            .trainerName(newTrainerName)
            .memberName(dto.getMemberName())
            .status("변경")
            .countChange(remainingCount)
            .totalAmount(transferAmount)
            .consumeAmount(0)
            .build();
        logDao.insertPtChangeLog(newLog);
    }
    
    /* PT 로그 직접 수정 */
    @Override
    @Transactional
    public void updatePtLogManual(PtLogDto dto) {
        logDao.updatePtLogManual(dto);
    }

    /* 회원의 남은 PT 횟수 조회 */
    @Override
    public int getRemainingPtCount(long memNum) {
        return logDao.selectRemainingPtCount(memNum);
    }
}
