package com.example.gymerp.repository;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.example.gymerp.dto.PtLogDto;
import com.example.gymerp.dto.VoucherLogDto;

import lombok.RequiredArgsConstructor;

@Primary
@Repository
@RequiredArgsConstructor
public class LogDaoImpl implements LogDao {

    private final SqlSession sqlSession;

    // 회원권 유효 여부 확인
    @Override
    public int checkVoucherValid(long memNum) {
        return sqlSession.selectOne("LogMapper.checkVoucherValid", memNum);
    }

    // 회원권 만료 여부 확인
    @Override
    public int checkVoucherExpired(long memNum) {
        return sqlSession.selectOne("LogMapper.checkVoucherExpired", memNum);
    }

    // 회원권 단건 조회
    @Override
    public VoucherLogDto selectVoucherByMember(long memNum) {
        return sqlSession.selectOne("LogMapper.selectVoucherByMember", memNum);
    }

    // 회원권 신규 등록
    @Override
    public int insertVoucherLog(VoucherLogDto dto) {
        return sqlSession.insert("LogMapper.insertVoucherLog", dto);
    }

    // 회원권 기간 연장
    @Override
    public int updateVoucherLog(VoucherLogDto dto) {
        return sqlSession.update("LogMapper.updateVoucherLog", dto);
    }

    // 회원권 로그 직접 수정
    @Override
    public int updateVoucherLogManual(VoucherLogDto dto) {
        return sqlSession.update("LogMapper.updateVoucherLogManual", dto);
    }

    // 회원권 삭제 또는 회귀 처리
    @Override
    public int rollbackOrDeleteVoucherLog(long memNum) {
        return sqlSession.delete("LogMapper.rollbackOrDeleteVoucherLog", memNum);
    }

    // PT 충전 로그 등록
    @Override
    public int insertPtChargeLog(PtLogDto dto) {
        return sqlSession.insert("LogMapper.insertPtChargeLog", dto);
    }

    // PT 소비 로그 등록
    @Override
    public int insertPtConsumeLog(PtLogDto dto) {
        return sqlSession.insert("LogMapper.insertPtConsumeLog", dto);
    }

    // PT 변경 로그 등록
    @Override
    public int insertPtChangeLog(PtLogDto dto) {
        return sqlSession.insert("LogMapper.insertPtChangeLog", dto);
    }

    // PT 전체 환불 로그 등록
    @Override
    public int insertPtFullRefundLog(PtLogDto dto) {
        return sqlSession.insert("LogMapper.insertPtFullRefundLog", dto);
    }

    // PT 로그 직접 수정
    @Override
    public int updatePtLogManual(PtLogDto dto) {
        return sqlSession.update("LogMapper.updatePtLogManual", dto);
    }

    // PT 로그 삭제 (예외적 정리용)
    @Override
    public int deletePtLogBySaleInfo(Map<String, Object> param) {
        return sqlSession.delete("LogMapper.deletePtLogBySaleInfo", param);
    }

    // 남은 PT 횟수 조회
    @Override
    public int selectRemainingPtCount(long memNum) {
        return sqlSession.selectOne("LogMapper.selectRemainingPtCount", memNum);
    }

    // 기존 트레이너 번호 조회
    @Override
    public Long selectOldTrainerEmpNum(long memNum) {
        return sqlSession.selectOne("LogMapper.selectOldTrainerEmpNum", memNum);
    }

    // 기존 트레이너 이름 조회
    @Override
    public String selectOldTrainerName(long memNum) {
        return sqlSession.selectOne("LogMapper.selectOldTrainerName", memNum);
    }

    // 총 PT 판매 횟수 조회
    @Override
    public int selectTotalPtCount(long memNum) {
        return sqlSession.selectOne("LogMapper.selectTotalPtCount", memNum);
    }

    // 총 PT 판매 금액 조회
    @Override
    public int selectTotalPtAmount(long memNum) {
        return sqlSession.selectOne("LogMapper.selectTotalPtAmount", memNum);
    }
}
