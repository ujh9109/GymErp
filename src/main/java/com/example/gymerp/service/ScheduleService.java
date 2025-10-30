package com.example.gymerp.service;

import java.util.List;

import com.example.gymerp.dto.ScheduleDto;

public interface ScheduleService {
	
	//전체 스케줄 조회
	List<ScheduleDto> getAllSchedules();
	
	//특정 날짜 스케줄 조회
	List<ScheduleDto> getSchedulesByDate(String date);
	
	//특정 직원 스케줄 조회
	List<ScheduleDto> getSchedulesByEmp(int empNum);
	
	//단건 스케줄 조회
	ScheduleDto getSchedule(int shNum);

	//스케줄 등록
	int addSchedule(ScheduleDto schedule);
	
	//스케줄 수정
	int updateSchedule(ScheduleDto schedule);
	
	//스케줄 삭제
	int deleteSchedule(int shNum);
}
