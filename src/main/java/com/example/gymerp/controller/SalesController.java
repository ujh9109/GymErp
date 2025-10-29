package com.example.gymerp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.example.gymerp.service.SalesServiceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class SalesController {

    private final SalesServiceService salesServiceService;

    /* ================================
       [서비스 판매 내역 조회]
    ================================ */

    // 서비스 판매 내역 조회 (페이지 단위 / 스크롤)
    @GetMapping("/sales/service/tables")
    public Map<String, Object> getPagedServiceSales(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "1") int scrollStep,
            @RequestParam(required = false) Long empNum,
            @RequestParam(required = false) Long memNum,
            @RequestParam(required = false) List<Long> serviceIds,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {

        return salesServiceService.getPagedServiceSales(
                keyword, page, scrollStep, empNum, memNum,
                serviceIds, startDate, endDate
        );
    }

    /* ================================
       [서비스 매출 그래프 조회]
    ================================ */

    // 서비스 매출 그래프 조회
    @GetMapping("/sales/service/graphs")
    public List<Map<String, Object>> getServiceSalesGraph(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) List<Long> serviceIds,
            @RequestParam(required = false) Long memNum,
            @RequestParam(required = false) Long empNum) {

        return salesServiceService.getServiceSalesGraph(
                startDate, endDate, serviceIds, memNum, empNum
        );
    }
}
