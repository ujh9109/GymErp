package com.example.gymerp.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.example.gymerp.dto.ScheduleDto;

public interface ScheduleDao {
    int insert(ScheduleDto dto);
    int update(ScheduleDto dto);
    int delete(Integer shNum);

    ScheduleDto selectOne(Integer shNum);
    List<ScheduleDto> selectRange(LocalDateTime from, LocalDateTime to, Integer empNum, String refType);

    int updateTime(Integer shNum, LocalDateTime startTime, LocalDateTime endTime);
}
