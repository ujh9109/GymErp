package com.example.gymerp.controller;

import java.util.List;


import org.springframework.web.bind.annotation.*;

import com.example.gymerp.dto.SalesService;
import com.example.gymerp.service.SalesServiceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class SalesServiceController {

    private final SalesServiceService salesServiceService;

    // 전체 서비스 판매 내역 조회
    @GetMapping("/sales/services")
    public List<SalesService> listSalesServices() {
        return salesServiceService.getAllSalesServices();
    }

    // 단일 서비스 판매 내역 조회
    @GetMapping("/sales/services/{id}")
    public SalesService getSalesService(@PathVariable("id") Long id) {
        return salesServiceService.getSalesServiceById(id);
    }

    // 서비스 판매 등록
    @PostMapping("/sales/services")
    public int createSalesService(@RequestBody SalesService salesService) {
        return salesServiceService.createSalesService(salesService);
    }

    // 서비스 판매 수정
    @PutMapping("/sales/services/{id}/edit")
    public int updateSalesService(@PathVariable("id") Long id, @RequestBody SalesService salesService) {
        salesService.setServiceSalesId(id);
        return salesServiceService.updateSalesService(salesService);
    }

    // 서비스 판매 삭제 (status = 'DELETED')
    @PutMapping("/sales/services/{id}/delete")
    public int deleteSalesService(@PathVariable("id") Long id) {
        return salesServiceService.deleteSalesService(id);
    }

    
}
