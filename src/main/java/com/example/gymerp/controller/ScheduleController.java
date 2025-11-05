package com.example.gymerp.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.gymerp.dto.ScheduleDto;
import com.example.gymerp.service.ScheduleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 전체일정 조회 
    @GetMapping("/schedule/all")
    public ResponseEntity<List<ScheduleDto>> getAllSchedules() {
        List<ScheduleDto> list = scheduleService.getAllSchedules();
        return ResponseEntity.ok(list);
    }

    // 일정 상세 조회
    @GetMapping("/schedule/{shNum}")
    public ResponseEntity<ScheduleDto> getScheduleById(@PathVariable int shNum) {
        ScheduleDto dto = scheduleService.getScheduleById(shNum);
        return ResponseEntity.ok(dto);
    }

    // 직원별 일정 조회
    @GetMapping("/schedule/emp/{empNum}")
    public ResponseEntity<List<ScheduleDto>> getSchedulesByEmp(@PathVariable int empNum) {
        List<ScheduleDto> list = scheduleService.getSchedulesByEmpNum(empNum);
        return ResponseEntity.ok(list);
    }

   	// 날짜 범위 조회 (달력)
    @GetMapping("/schedule/range")
    public ResponseEntity<List<ScheduleDto>> getSchedulesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        List<ScheduleDto> list = scheduleService.getSchedulesByDateRange(startDate, endDate);
        return ResponseEntity.ok(list);
    }

    // 일정 등록 
    @PostMapping("/schedule/add")
    public ResponseEntity<String> createSchedule(@RequestBody ScheduleDto scheduleDto) {
        scheduleService.createSchedule(scheduleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("일정이 등록되었습니다.");
    }

    // 일정 수정 
    @PutMapping("/schedule/update")
    public ResponseEntity<String> updateSchedule(@RequestBody ScheduleDto scheduleDto) {
        scheduleService.updateSchedule(scheduleDto);
        return ResponseEntity.ok("일정이 수정되었습니다.");
    }
    
    // 일정 삭제 
    @DeleteMapping("/schedule/delete/{shNum}")
    public ResponseEntity<String> deleteSchedule(@PathVariable int shNum) {
        scheduleService.deleteSchedule(shNum);
        return ResponseEntity.ok("일정이 삭제되었습니다.");
    }

}