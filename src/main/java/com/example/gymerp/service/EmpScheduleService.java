package com.example.gymerp.service;

import java.time.LocalDateTime;
import java.util.List;

import com.example.gymerp.dto.EmpScheduleDto;

public interface EmpScheduleService {

    /** ============================= 일정 조회 ============================= */

    // 1. 전체 일정 조회 (PT / Vacation / Etc 포함)
    List<EmpScheduleDto> getAllSchedules();

    // 2. 일정 단건 조회 (PK 기준)
    EmpScheduleDto getScheduleByCalNum(int calNum);

    // 3. 직원 + 날짜 범위 일정 조회
    List<EmpScheduleDto> getSchedulesByEmpAndDate(int empNum, LocalDateTime startDate, LocalDateTime endDate);


    /** ============================= 일정 등록 ============================= */

    // 4. ETC 전용 일정 등록
    int createEtcSchedule(EmpScheduleDto dto);

    // 5. VACATION 전용 일정 등록
    int createEmpVacationSchedule(EmpScheduleDto dto);

    // 6. REGISTRATION 전용 일정 등록 (회원 정보 포함)
    int createEmpRegistrationSchedule(EmpScheduleDto dto);


    /** ============================= 일정 수정 및 삭제 ============================= */

    // 7. 일정 수정
    int updateSchedule(EmpScheduleDto dto);

    // 8. 일정 삭제
    int deleteSchedule(int calNum);
}
