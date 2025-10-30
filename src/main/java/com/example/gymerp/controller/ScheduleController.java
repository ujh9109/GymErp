package com.example.gymerp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.gymerp.dto.ScheduleDto;
import com.example.gymerp.service.ScheduleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/Schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 전체 스케줄 조회
    @GetMapping
    public List<ScheduleDto> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    // 특정 날짜 스케줄 조회
    @GetMapping("/date/{date}")
    public List<ScheduleDto> getSchedulesByDate(@PathVariable String date) {
        return scheduleService.getSchedulesByDate(date);
    }

    // 특정 직원 스케줄 조회
    @GetMapping("/emp/{empNum}")
    public List<ScheduleDto> getSchedulesByEmpNum(@PathVariable int empNum) {
        return scheduleService.getSchedulesByEmp(empNum);
    }

    // 단건 조회
    @GetMapping("/{shNum}")
    public ScheduleDto getSchedule(@PathVariable int shNum) {
        return scheduleService.getSchedule(shNum);
    }

    // 스케줄 등록
    @PostMapping
    public int addSchedule(@RequestBody ScheduleDto schedule) {
        return scheduleService.addSchedule(schedule);
    }

    // 스케줄 수정
    @PutMapping("/{shNum}")
    public int updateSchedule(@PathVariable int shNum, @RequestBody ScheduleDto schedule) {
        schedule.setShNum(shNum); // path 변수로 들어온 shNum 적용
        return scheduleService.updateSchedule(schedule);
    }

    // 스케줄 삭제
    @DeleteMapping("/{shNum}")
    public int deleteSchedule(@PathVariable int shNum) {
        return scheduleService.deleteSchedule(shNum);
    }
}
