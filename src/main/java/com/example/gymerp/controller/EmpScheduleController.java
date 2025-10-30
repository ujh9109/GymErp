package com.example.gymerp.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.gymerp.dto.EmpScheduleDto;
import com.example.gymerp.service.EmpScheduleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("EmpSchedule")
@RequiredArgsConstructor
public class EmpScheduleController {
	
	private final EmpScheduleService empScheduleService;
	
	  /** 전체 일정 조회 */
    @GetMapping("/all")
    public List<EmpScheduleDto> getAllSchedules() {
        return empScheduleService.getAllSchedules();
    }

    /** PK 기준 단건 조회 */
    @GetMapping("/{calNum}")
    public EmpScheduleDto getSchedule(@PathVariable int calNum) {
        return empScheduleService.getScheduleByCalNum(calNum);
    }

    /** 직원 + 날짜 범위로 일정 조회 */
    @GetMapping("/emp/{empNum}")
    public List<EmpScheduleDto> getSchedulesByEmpAndDate(
            @PathVariable int empNum,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return empScheduleService.getSchedulesByEmpAndDate(empNum, startDate, endDate);
    }

    /** 일정 등록 (PT / VACATION / ETC) */
    @PostMapping
    public int addSchedule(@RequestBody EmpScheduleDto dto) {
        return empScheduleService.addSchedule(dto);
    }

    /** 일정 수정 */
    @PutMapping("/{calNum}")
    public int updateSchedule(@PathVariable int calNum, @RequestBody EmpScheduleDto dto) {
        dto.setCalNum(calNum); // PK 세팅
        return empScheduleService.updateSchedule(dto);
    }

    /** 일정 삭제 */
    @DeleteMapping("/{calNum}")
    public int deleteSchedule(@PathVariable int calNum) {
        return empScheduleService.deleteSchedule(calNum);
    }
}
