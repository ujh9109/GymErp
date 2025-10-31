package com.example.gymerp.controller;

import java.net.URI;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.gymerp.dto.EmpAttendanceDto;
import com.example.gymerp.service.EmpAttendanceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class EmpAttendanceController {

    private final EmpAttendanceService service;

    // 전체 목록
    @GetMapping("/attendances")
    public List<EmpAttendanceDto> getAll() {
        return service.getAllEmpAttendances();
    }

    // 직원별 목록 (?empNum=123)
    @GetMapping(value = "/attendances", params = "empNum")
    public List<EmpAttendanceDto> getByEmp(@RequestParam int empNum) {
        return service.getEmpAttendancesByEmpNum(empNum);
    }

    // 단건 조회
    @GetMapping("/attendances/{attNum}")
    public EmpAttendanceDto getOne(@PathVariable int attNum) {
        return service.getEmpAttendanceById(attNum);
    }

    // 출근(등록)
    @PostMapping("/attendances")
    public ResponseEntity<Void> checkIn(@RequestBody EmpAttendanceDto dto) {
        int rows = service.addEmpAttendance(dto);
        return rows > 0
            ? ResponseEntity.created(URI.create("/v1/attendances")).build()
            : ResponseEntity.badRequest().build();
    }

    // 퇴근시간 업데이트 (예: checkOut=2025-10-29T18:30:00)
    @PatchMapping("/attendances/{attNum}/checkout")
    public ResponseEntity<Void> checkOut(
            @PathVariable int attNum,
            @RequestParam String checkOut
    ) {
        LocalDateTime ldt = LocalDateTime.parse(checkOut, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        service.updateEmpAttendanceCheckOut(attNum, Timestamp.valueOf(ldt));
        return ResponseEntity.noContent().build();
    }

    // 전체 수정
    @PutMapping("/attendances/{attNum}")
    public ResponseEntity<Void> update(@PathVariable int attNum, @RequestBody EmpAttendanceDto dto) {
        dto.setAttNum(attNum);
        service.updateEmpAttendance(dto);
        return ResponseEntity.noContent().build();
    }

    // 삭제
    @DeleteMapping("/attendances/{attNum}")
    public ResponseEntity<Void> delete(@PathVariable int attNum) {
        service.deleteEmpAttendance(attNum);
        return ResponseEntity.noContent().build();
    }
}
