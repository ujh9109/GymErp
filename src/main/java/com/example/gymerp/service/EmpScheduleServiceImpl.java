package com.example.gymerp.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gymerp.dto.EmpScheduleDto;
import com.example.gymerp.repository.EmpScheduleDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EmpScheduleServiceImpl implements EmpScheduleService{
	
	private final EmpScheduleDao empScheduleDao;

	
	@Override
	public List<EmpScheduleDto> getAllSchedules() {
		return empScheduleDao.selectAll();
	}

	// 단건 일정 조회 (calNum 기준)
	@Override
	public EmpScheduleDto getScheduleByCalNum(int calNum) {
		return empScheduleDao.selectByCalNum(calNum);
	}

	 // 직원 + 날짜 범위로 일정 조회
	@Override
	public List<EmpScheduleDto> getSchedulesByEmpAndDate(int empNum, LocalDate startDate, LocalDate endDate) {
		 return empScheduleDao.selectByEmpAndDate(empNum, startDate, endDate);
	}

	// 일정 수정
	public int updateSchedule(EmpScheduleDto dto) {
		  return empScheduleDao.update(dto);
	}

	   // 일정 삭제
	public int deleteSchedule(int calNum) {
		return empScheduleDao.delete(calNum);
	}

	   // 일정 등록
	public int addSchedule(EmpScheduleDto dto) {
	    
        return empScheduleDao.insert(dto);
	}
}

	
