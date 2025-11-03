package com.example.gymerp.service;

import java.time.LocalDateTime;
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

    private final ScheduleDao scheduleDao;

    // 전체 일정 조회 
    @Override
    public List<ScheduleDto> getAllSchedules() {
        return scheduleDao.selectAll();
    }

    //단건 조회 
    @Override
    public ScheduleDto getScheduleById(int shNum) {
        return scheduleDao.selectByShNum(shNum);
    }

    //직원별 일정 조회 
    @Override
    public List<ScheduleDto> getSchedulesByEmpNum(int empNum) {
        return scheduleDao.selectByEmpNum(empNum);
    }

    // 날짜 범위 조회
    @Override
    public List<ScheduleDto> getSchedulesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return scheduleDao.selectByDateRange(startDate, endDate);
    }

    //일정 등록
    @Override
    public int createSchedule(ScheduleDto schedule) {
        if (schedule.getEmpNum() <= 0) {
            throw new IllegalArgumentException("직원번호가 유효하지 않음");
        }
        return scheduleDao.insert(schedule);
    }

    //일정 수정 
    @Override
    public int updateSchedule(ScheduleDto schedule) {
        if (schedule.getShNum() <= 0) {
            throw new IllegalArgumentException("수정할 일정 번호가 유효하지 않음");
        }
        return scheduleDao.update(schedule);
    }

    //일정 삭제
    @Override
    public int deleteSchedule(int shNum) {
        if (shNum <= 0) {
            throw new IllegalArgumentException("삭제할 일정 번호가 유효하지 않음");
        }
        return scheduleDao.delete(shNum);
    }
}
