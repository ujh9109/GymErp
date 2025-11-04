package com.example.gymerp.service;

import java.util.List;

import com.example.gymerp.dto.PtRegistrationDto;

public interface PtRegistrationService {

    // ===============================
    // [기존 CRUD 기능]
    // ===============================

    // 전체 예약 목록 조회
    List<PtRegistrationDto> getAllPtRegistration(Integer empNum, String date);

    // 단일 예약 조회
    PtRegistrationDto getPtRegistrationById(int regNum);

    // 예약 등록
    int insertPtRegistration(PtRegistrationDto dto);

    // 예약 수정
    int updatePtRegistration(PtRegistrationDto dto);

    // 예약 삭제
    int deletePtRegistration(int regNum);


    // ===============================
    // [PT LOG 관련 추가 기능]
    // ===============================

    /**
     * PT 예약 시 처리:
     *  - REGISTRATION 테이블에 예약 정보 저장
     *  - PT_LOG 테이블에 소비 로그(-1) 기록
     */
    //void registerPtWithLog(PtRegistrationDto dto);

    /**
     * PT 예약 취소 시 처리:
     *  - 기존 소비 로그 조회
     *  - PT_LOG 테이블에 예약취소 로그(+1) 기록
     *  - REGISTRATION 테이블에서 예약 삭제
     */
    //void cancelPtWithLog(long regNum);
}
