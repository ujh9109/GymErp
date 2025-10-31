package com.example.gymerp.repository;

import com.example.gymerp.dto.PtLogDto;
import com.example.gymerp.dto.VoucherLogDto;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogDao {

    // 회원권 유효 여부 확인
    int checkVoucherValid(long memNum);

    // 회원권 만료 여부 확인
    int checkVoucherExpired(long memNum);

    // 회원권 단건 조회
    VoucherLogDto selectVoucherByMember(long memNum);

    // 회원권 신규 등록
    int insertVoucherLog(VoucherLogDto dto);

    // 회원권 기간 연장
    int updateVoucherLog(VoucherLogDto dto);

    // 회원권 로그 직접 수정
    int updateVoucherLogManual(VoucherLogDto dto);

    // 회원권 삭제 또는 회귀 처리
    int rollbackOrDeleteVoucherLog(long memNum);

    // PT 충전 로그 등록
    int insertPtChargeLog(PtLogDto dto);

    // PT 소비 로그 등록
    int insertPtConsumeLog(PtLogDto dto);

    // PT 변경 로그 등록
    int insertPtChangeLog(PtLogDto dto);

    // PT 전체 환불 로그 등록
    int insertPtFullRefundLog(PtLogDto dto);

    // PT 로그 직접 수정
    int updatePtLogManual(PtLogDto dto);

    // PT 로그 삭제 (예외적 정리용)
    int deletePtLogBySaleInfo(Map<String, Object> param);

    // 남은 PT 횟수 조회
    int selectRemainingPtCount(long memNum);

    // 기존 트레이너 번호 조회
    Long selectOldTrainerEmpNum(long memNum);

    // 기존 트레이너 이름 조회
    String selectOldTrainerName(long memNum);

    // 총 PT 판매 횟수 조회
    int selectTotalPtCount(long memNum);

    // 총 PT 판매 금액 조회
    int selectTotalPtAmount(long memNum);
}
