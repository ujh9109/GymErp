package com.example.gymerp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.gymerp.service.EmpService;

import lombok.RequiredArgsConstructor;

import com.example.gymerp.dto.EmpDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1") 
public class EmpController {

    private final EmpService empService;

    // 전체 직원 목록 조회 api
    @GetMapping("/list")
    public List<EmpDto> getEmpList() {
        return empService.getAllEmp();
    }
    
    // 직원 상세 조회
    @GetMapping("/{empNum}")
    public EmpDto getEmp(@PathVariable int empNum) {
        return empService.getEmpByNum(empNum);
    }

    // 직원 등록
    @PostMapping
    public String insert(@RequestBody EmpDto dto) {
        empService.insertEmp(dto);
        return "success";
    }

    // 직원 수정
    @PutMapping("/{empNum}")
    public String update(@PathVariable int empNum, @RequestBody EmpDto dto) {
        dto.setEmpNum(empNum);
        empService.updateEmp(dto);
        return "success";
    }

    // 직원 삭제
    @DeleteMapping("/{empNum}")
    public String delete(@PathVariable int empNum) {
        EmpDto dto = new EmpDto();
        dto.setEmpNum(empNum);
        empService.deleteEmp(dto);
        return "success";
    }
    
}
