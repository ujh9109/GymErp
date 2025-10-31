// src/main/java/com/example/gymerp/controller/EmpAttendanceController.java
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
    @GetMapping("/attendance")
    public List<EmpAttendanceDto> getAll() {
        return service.getAllEmpAttendances();
    }

    // 직원별 목록 (?empNum=123)
    @GetMapping(value = "/attendance", params = "empNum")
    public List<EmpAttendanceDto> getByEmp(@RequestParam int empNum) {
        return service.getEmpAttendancesByEmpNum(empNum);
    }

    // 단건 조회
    @GetMapping("/attendance/{attNum}")
    public EmpAttendanceDto getOne(@PathVariable int attNum) {
        return service.getEmpAttendanceById(attNum);
    }

    // 출근(등록) - 바디로 empNum 전달(보안 미구현 상태용)
    @PostMapping("/attendance")
    public ResponseEntity<Void> checkIn(@RequestBody EmpAttendanceDto dto) {
        int rows = service.addEmpAttendance(dto);
        return rows > 0
                ? ResponseEntity.created(URI.create("/v1/attendance")).build() // 단수 경로로 수정
                : ResponseEntity.badRequest().build();
    }

    // 퇴근시간 업데이트 (checkOut 없으면 now 로 처리)
    @PatchMapping("/attendance/{attNum}/checkout")
    public ResponseEntity<Void> checkOut(
            @PathVariable int attNum,
            @RequestParam(required = false) String checkOut
    ) {
        Timestamp ts = (checkOut == null || checkOut.isBlank())
                ? Timestamp.valueOf(LocalDateTime.now())
                : Timestamp.valueOf(LocalDateTime.parse(checkOut, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        service.updateEmpAttendanceCheckOut(attNum, ts);
        return ResponseEntity.noContent().build();
    }

    // 전체 수정
    @PutMapping("/attendance/{attNum}")
    public ResponseEntity<Void> update(@PathVariable int attNum, @RequestBody EmpAttendanceDto dto) {
        dto.setAttNum(attNum);
        service.updateEmpAttendance(dto);
        return ResponseEntity.noContent().build();
    }

    // 삭제
    @DeleteMapping("/attendance/{attNum}")
    public ResponseEntity<Void> delete(@PathVariable int attNum) {
        service.deleteEmpAttendance(attNum);
        return ResponseEntity.noContent().build();
    }
}
