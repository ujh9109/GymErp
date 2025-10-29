package com.example.gymerp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.gymerp.dto.EmpVacationDto;
import com.example.gymerp.service.EmpVacationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class EmpVacationController {

    private final EmpVacationService service;

    // 전체 목록
    @GetMapping("/vacations")
    public List<EmpVacationDto> getAllVacations() {
        return service.getAllEmpVacations();
    }

    // 직원별 목록
    @GetMapping(value = "/vacations", params = "empNum")
    public List<EmpVacationDto> getVacationsByEmp(@RequestParam int empNum) {
        return service.getEmpVacationsByEmpNum(empNum);
    }

    // 단건 조회
    @GetMapping("/vacations/{vacNum}")
    public EmpVacationDto getVacationById(@PathVariable int vacNum) {
        return service.getEmpVacationById(vacNum);
    }

    // 등록
    @PostMapping("/vacations")
    public ResponseEntity<Integer> addVacation(@RequestBody EmpVacationDto dto) {
        int rows = service.addEmpVacation(dto);
        return ResponseEntity.ok(rows);
    }

    // 전체 수정
    @PutMapping("/vacations/{vacNum}")
    public ResponseEntity<Void> updateVacation(@PathVariable int vacNum, @RequestBody EmpVacationDto dto) {
        dto.setVacNum(vacNum);
        service.updateEmpVacation(dto);
        return ResponseEntity.noContent().build();
    }

    //test1
    // 상태만 변경
    @PatchMapping("/vacations/{vacNum}/state")
    public ResponseEntity<Void> updateVacationState(@PathVariable int vacNum, @RequestParam String state) {
        service.updateEmpVacationState(vacNum, state);
        return ResponseEntity.noContent().build();
    }

    // 삭제
    @DeleteMapping("/vacations/{vacNum}")
    public ResponseEntity<Void> deleteVacation(@PathVariable int vacNum) {
        service.deleteEmpVacation(vacNum);
        return ResponseEntity.noContent().build();
    }
}
