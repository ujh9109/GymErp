package com.example.gymerp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.gymerp.service.SalesServiceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class SalesController {
	
	private final com.example.gymerp.service.SalesItemService salesItemService;
	private final SalesServiceService salesServiceService;
	
	// 서비스 매출 통계 조회
    @GetMapping("/sales/services/analytics")
    public List<Map<String, Object>> getServiceSalesAnalytics(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) List<Long> serviceIds,
            @RequestParam(required = false) Long memNum,
            @RequestParam(required = false) Long empNum
    ) {
        return salesServiceService.getServiceSalesAnalytics(startDate, endDate, serviceIds, memNum, empNum);
    }
}
