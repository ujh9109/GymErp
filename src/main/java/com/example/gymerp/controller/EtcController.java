package com.example.gymerp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.gymerp.dto.EtcDto;
import com.example.gymerp.service.EtcService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/etc")
@RequiredArgsConstructor
public class EtcController {

    private final EtcService etcService;
    
    
    // 1. 일정 목록 조회
    @GetMapping
    public List<EtcDto> getAllEtc() {
        return etcService.getAllEtc(); // 서비스에서 전체 조회
    }

    // 1. Etc 일정 등록
    @PostMapping
    public int createEtc(@RequestBody EtcDto etcDto) {
        
    	return etcService.createEtc(etcDto);
    }


    // 2. 특정 Etc 일정 조회
    @GetMapping("/{etcNum}")
    public EtcDto getEtc(@PathVariable int etcNum) {
        return etcService.getEtc(etcNum);
    }

    // 3. Etc 일정 수정
    @PutMapping("/{etcNum}")
    public int updateEtc(@PathVariable int etcNum, @RequestBody EtcDto etcDto) {
        etcDto.setEtcNum(etcNum);
        return etcService.updateEtc(etcDto);
    }

    // 4. Etc 일정 삭제
    @DeleteMapping("/{etcNum}")
    public int deleteEtc(@PathVariable int etcNum) {
        return etcService.deleteEtc(etcNum);
    }

   
}
