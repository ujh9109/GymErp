package com.example.gymerp.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.example.gymerp.dto.EmpScheduleDto;
import com.example.gymerp.dto.EtcDto;
import com.example.gymerp.repository.EmpScheduleDao;
import com.example.gymerp.repository.EtcDao;

public interface EmpScheduleService {
	
	// 1. 전체 일정 조회 (PT / Vacation / Etc 포함)
	List<EmpScheduleDto> getAllSchedules();

	//2. 일정 단건 조회 (PK 기준)
	EmpScheduleDto getScheduleByCalNum(int calNum);

	// 3. 직원 + 날짜 범위 일정 조회
	List<EmpScheduleDto> getSchedulesByEmpAndDate(int empNum, LocalDateTime startDate, LocalDateTime endDate);

	// 4. 일정 등록 (통합: Registration / Vacation / Etc)
	int addSchedule(EmpScheduleDto dto); // PT/VACATION/ETC 통합 등록
	
	//5. ETC 전용 일정 등록 (ETC 테이블 + EmpSchedule 연계)
	int createEtcSchedule(EmpScheduleDto dto); // ETC 전용 등록
	
	//6. 일정 수정
	int updateSchedule(EmpScheduleDto dto);

	//7. 일정 삭제
	int deleteSchedule(int calNum);
}
