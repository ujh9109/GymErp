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

    /* ===============================
       [회원권 관련]
    =============================== */

    // 회원권 유효 여부 확인
    @Override
    public boolean isVoucherValid(long memNum) {
        return logDao.checkVoucherValid(memNum) > 0;
    }

    // 회원권 단건 조회
    @Override
    public VoucherLogDto getVoucherByMember(long memNum) {
        return logDao.selectVoucherByMember(memNum);
    }

    // 회원권 신규 등록
    @Override
    @Transactional
    public void insertVoucherLog(VoucherLogDto dto) {
        logDao.insertVoucherLog(dto);
    }

    // 회원권 기간 연장 (endDate 기준 누적)
    @Override
    @Transactional
    public void extendVoucherLog(VoucherLogDto dto) {
        logDao.extendVoucherLog(dto);
    }

    // 회원권 만료 후 재시작 (startDate = SYSDATE)
    @Override
    @Transactional
    public void renewVoucherLog(VoucherLogDto dto) {
        logDao.renewVoucherLog(dto);
    }

    // 회원권 부분환불 (endDate 단축)
    @Override
    @Transactional
    public void partialRefundVoucherLog(VoucherLogDto dto) {
        logDao.partialRefundVoucherLog(dto);
    }

    // 회원권 전체환불 (이전 상태로 롤백)
    @Override
    @Transactional
    public void rollbackVoucherLog(VoucherLogDto dto) {
        logDao.rollbackVoucherLog(dto);
    }


    /* ===============================
       [PT 로그 관련]
    =============================== */

    // PT 신규 충전 로그 등록
    @Override
    @Transactional
    public void addPtChargeLog(PtLogDto dto) {
        logDao.insertPtChargeLog(dto);
    }

    // PT 연장 (기존 row update)
    @Override
    @Transactional
    public void extendPtLog(PtLogDto dto) {
        logDao.updatePtLogExtension(dto);
    }

    // PT 부분환불 로그 등록
    @Override
    @Transactional
    public void addPtPartialRefundLog(PtLogDto dto) {
        logDao.insertPtPartialRefundLog(dto);
    }

    // PT 전체환불 로그 등록
    @Override
    @Transactional
    public void addPtFullRefundLog(PtLogDto dto) {
        logDao.insertPtFullRefundLog(dto);
    }

    // 남은 PT 횟수 조회
    @Override
    public int getRemainingPtCount(long memNum) {
        return logDao.selectRemainingPtCount(memNum);
    }

    // 특정 판매건(refundId 기준)의 PT 로그 단건 조회
    @Override
    public PtLogDto getPtLogByRefundId(long refundId) {
        return logDao.selectPtLogByUsageId(refundId);
    }
}
