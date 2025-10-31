package com.example.gymerp.service;

import java.util.List;

import com.example.gymerp.dto.PtRegistrationDto;

public interface PtRegistrationService {

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
}
