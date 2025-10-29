package com.example.gymerp.service;

import com.example.gymerp.dto.PtLogDto;
import com.example.gymerp.dto.VoucherLogDto;

public interface LogService {

    /* 회원권 유효 여부 확인 */
    boolean isVoucherValid(long memNum);

    /* 회원권 단건 조회 */
    VoucherLogDto getVoucherByMember(long memNum);

    /* 회원권 등록 또는 갱신 */
    void saveOrUpdateVoucher(VoucherLogDto dto);
    
    /* 회원권 로그 직접 수정 */
    void updateVoucherLogManual(VoucherLogDto dto);


    /* PT 충전 로그 등록 */
    void addPtChargeLog(PtLogDto dto);

    /* PT 소비 로그 등록 */
    void addPtConsumeLog(PtLogDto dto);

    /* PT 변경 로그 등록 */
    void addPtChangeLog(PtLogDto dto);
    
    /* PT 로그 직접 수정 */
    void updatePtLogManual(PtLogDto dto);

    /* 남은 PT 횟수 조회 */
    int getRemainingPtCount(long memNum);
}
