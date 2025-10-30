package com.example.gymerp.service;

import java.time.LocalDate;
import java.util.List;

import com.example.gymerp.dto.EmpScheduleDto;

public interface EmpScheduleService {
	
	// 전체 일정 조회
    List<EmpScheduleDto> getAllSchedules();

    // PK 단건 조회
    EmpScheduleDto getScheduleByCalNum(int calNum);

    // 직원 + 날짜 범위 조회
    List<EmpScheduleDto> getSchedulesByEmpAndDate(int empNum, LocalDate startDate, LocalDate endDate);

    // 일정 수정
    int updateSchedule(EmpScheduleDto dto);

    // 일정 삭제
    int deleteSchedule(int calNum);

    // 일정 등록
    int addSchedule(EmpScheduleDto dto);

}
