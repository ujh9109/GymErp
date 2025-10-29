package com.example.gymerp.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.gymerp.dto.PtLogDto;
import com.example.gymerp.dto.VoucherLogDto;

@Mapper
public interface LogDao {

    /* 회원권 유효 여부 확인 */
    int checkVoucherValid(long memNum);

    /* 회원권 단건 조회 */
    VoucherLogDto selectVoucherByMember(long memNum);

    /* 회원권 로그 등록 */
    int insertVoucherLog(VoucherLogDto dto);

    /* 회원권 기간 연장 또는 재시작 */
    int updateVoucherLog(VoucherLogDto dto);
    
    /* 회원권 로그 직접 수정 */
    int updateVoucherLogManual(VoucherLogDto dto);


    /* PT 충전 로그 등록 */
    int insertPtChargeLog(PtLogDto dto);

    /* PT 소비 로그 등록 */
    int insertPtConsumeLog(PtLogDto dto);

    /* PT 변경 로그 등록 */
    int insertPtChangeLog(PtLogDto dto);

    /* PT 로그 직접 수정 */
    int updatePtLogManual(PtLogDto dto);
    
    /* 회원의 남은 PT 횟수 조회 */
    int selectRemainingPtCount(long memNum);

    /* 기존 트레이너 번호 조회 */
    Long selectOldTrainerEmpNum(Long memNum);

    /* 기존 트레이너 이름 조회 */
    String selectOldTrainerName(Long memNum);

    /* 총 판매 횟수 조회 */
    int selectTotalPtCount(Long memNum);

    /* 총 판매 금액 조회 */
    int selectTotalPtAmount(Long memNum);
}
