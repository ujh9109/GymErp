// src/main/java/com/example/gymerp/controller/ScheduleController.java
package com.example.gymerp.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.gymerp.dto.ScheduleDto;
import com.example.gymerp.service.ScheduleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService service;

    // ✅ 문자열(ISO_DATE_TIME) → LocalDateTime
    private static LocalDateTime ldt(String s){
        if (s == null || s.isBlank()) return null;
        return LocalDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME);
    }

    @PostMapping("/schedules")
    public ResponseEntity<Integer> create(@RequestBody ScheduleDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/schedules/{shNum}")
    public ScheduleDto get(@PathVariable Integer shNum) {
        return service.get(shNum);
    }

    @PutMapping("/schedules/{shNum}")
    public void update(@PathVariable Integer shNum, @RequestBody ScheduleDto dto) {
        dto.setShNum(shNum);
        service.update(dto);
    }

    @DeleteMapping("/schedules/{shNum}")
    public void delete(@PathVariable Integer shNum) {
        service.remove(shNum);
    }

    @GetMapping("/schedules")
    public List<ScheduleDto> list(
            @RequestParam(required=false) String from,
            @RequestParam(required=false) String to,
            @RequestParam(required=false) Integer empNum,
            @RequestParam(required=false) String refType) {
        // ✅ LocalDateTime 으로 변환해서 전달
        return service.getRange(ldt(from), ldt(to), empNum, refType);
    }

    @PatchMapping("/schedules/{shNum}/time")
    public void updateTime(@PathVariable Integer shNum,
                           @RequestParam String startTime,
                           @RequestParam String endTime) {
        // ✅ LocalDateTime 으로 변환해서 전달
        service.updateTime(shNum, ldt(startTime), ldt(endTime));
    }
}
