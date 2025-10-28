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

    // 서비스 판매 내역 전체 조회
    @GetMapping("/sales/services")
    public List<SalesService> getAllSalesServices() {
        return salesServiceService.getAllSalesServices();
    }

    // 서비스 판매 내역 단일 조회
    @GetMapping("/sales/services/{id}")
    public SalesService getSalesServiceById(@PathVariable("id") Long id) {
        return salesServiceService.getSalesServiceById(id);
    }

    // 서비스 판매 내역 등록
    @PostMapping("/sales/services")
    public int addSalesService(@RequestBody SalesService salesService) {
        return salesServiceService.addSalesService(salesService);
    }

    // 서비스 판매 내역 수정
    @PutMapping("/sales/services/{id}")
    public int updateSalesService(@PathVariable("id") Long id,
                                  @RequestBody SalesService salesService) {
        salesService.setServiceSalesId(id);
        return salesServiceService.updateSalesService(salesService);
    }

    // 서비스 판매 내역 삭제 (status = 'DELETED')
    @PutMapping("/sales/services/{id}/delete")
    public int deleteSalesService(@PathVariable("id") Long id) {
        return salesServiceService.deleteSalesService(id);
    }
}
