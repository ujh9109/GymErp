// src/main/java/com/example/gymerp/service/ScheduleService.java
package com.example.gymerp.service;

import java.time.LocalDateTime;
import java.util.List;
import com.example.gymerp.dto.ScheduleDto;

public interface ScheduleService {
    Integer create(ScheduleDto dto);
    void update(ScheduleDto dto);
    void remove(Integer shNum);
    ScheduleDto get(Integer shNum);

    // ▼ LocalDateTime으로 맞춤
    List<ScheduleDto> getRange(LocalDateTime from, LocalDateTime to, Integer empNum, String refType);
    void updateTime(Integer shNum, LocalDateTime startTime, LocalDateTime endTime);
}
