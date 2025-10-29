package com.example.gymerp.service;

import java.util.List;
import com.example.gymerp.dto.EmpVacationDto;

public interface EmpVacationService {

    // 전체 휴가 목록 조회
    List<EmpVacationDto> getAllEmpVacations();

    // 직원별 휴가 목록 조회
    List<EmpVacationDto> getEmpVacationsByEmpNum(int empNum);

    // 단일 휴가 조회
    EmpVacationDto getEmpVacationById(int vacNum);

    // 휴가 등록
    int addEmpVacation(EmpVacationDto dto);

    // 휴가 수정
    int updateEmpVacation(EmpVacationDto dto);

    // 휴가 상태 변경
    int updateEmpVacationState(int vacNum, String vacState);

    // 휴가 삭제
    int deleteEmpVacation(int vacNum);
}
