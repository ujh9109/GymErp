package com.example.gymerp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gymerp.dto.ScheduleDto;
import com.example.gymerp.repository.ScheduleDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

	// DAO 주입
	private final ScheduleDao scheduleDao;

	// 전체 스케줄 조회
	@Override
	public List<ScheduleDto> getAllSchedules() {

		return scheduleDao.selectAllSchedules();
	}

	// 특정 날짜 스케줄 조회
	@Override
	public List<ScheduleDto> getSchedulesByDate(String date) {

		return scheduleDao.findByDate(date);
	}

	// 특정 직원 스케줄 조회
	@Override
	public List<ScheduleDto> getSchedulesByEmp(int empNum) {

		return scheduleDao.findByEmpNum(empNum);
	}

	// 단건 스케줄 조회
	@Override
	public ScheduleDto getSchedule(int shNum) {

		return scheduleDao.findByShNum(shNum);
	}

	// 스케줄 등록
	@Override
	public int addSchedule(ScheduleDto schedule) {

		return scheduleDao.insert(schedule);
	}

	// 스케줄 수정
	@Override
	public int updateSchedule(ScheduleDto schedule) {

		 return scheduleDao.update(schedule);
	}

	// 스케줄 삭제
	@Override
	public int deleteSchedule(int shNum) {

		return scheduleDao.delete(shNum);
	}

}
