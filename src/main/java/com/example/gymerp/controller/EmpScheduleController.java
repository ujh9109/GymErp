package com.example.gymerp.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.gymerp.dto.EmpScheduleDto;
import com.example.gymerp.service.EmpScheduleService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(
	    origins = "http://localhost:5173",
	    allowedHeaders = "*",
	    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}
	)
@RestController
@RequestMapping("/empSchedule")
@RequiredArgsConstructor
public class EmpScheduleController {
	
	private final EmpScheduleService empScheduleService;

    /** 전체 일정 조회 */
    @GetMapping("/all")
    public List<EmpScheduleDto> getAllSchedules() {
        return empScheduleService.getAllSchedules();
    }

    /** ETC 일정 등록 */
    @PostMapping("/etc")
    public int createEtcSchedule(@RequestBody EmpScheduleDto dto) {
        // dto.refType = "etc" + dto.etc 포함
        return empScheduleService.createEtcSchedule(dto);
    }
    
    /** 일정 수정 */
    @PutMapping("/update")
    public int updateSchedule(@RequestBody EmpScheduleDto dto) {
        return empScheduleService.updateSchedule(dto);
    }

    /** 일정 삭제 */
    @DeleteMapping("/{calNum}")
    public int deleteSchedule(@PathVariable int calNum) {
        return empScheduleService.deleteSchedule(calNum);
    }
}
