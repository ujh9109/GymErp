package com.example.gymerp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class SalesController {

    // 상품(Items) 쪽 서비스
    private final com.example.gymerp.service.SalesItemService salesItemService;

    // 서비스(Service) 쪽 서비스
    private final com.example.gymerp.service.SalesServiceService salesServiceService;

    /* ================================
       [상품 매출 통계/그래프]
    ================================ */

    // 상품 매출 통계
    @GetMapping("/sales/items/analytics")
    public List<Map<String, Object>> getItemSalesAnalytics(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) List<Integer> itemIds,
            @RequestParam(required = false) Integer memNum,
            @RequestParam(required = false) Integer empNum
    ) {
    	return salesItemService.getItemSalesAnalytics(startDate, endDate, itemIds, memNum, empNum);
    }

    // 상품 매출 그래프 데이터
    @GetMapping("/sales/items/graph/data")
    public Map<String, List<Map<String, Object>>> getItemSalesGraphData(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String groupByUnit // "DAY" | "MONTH"
    ) {
        return salesItemService.getItemSalesGraphData(startDate, endDate, groupByUnit);
    }

    /* ================================
       [서비스 판매 내역/그래프]
    ================================ */

    // 서비스 판매 내역 조회(페이지/스크롤)
    @GetMapping("/sales/service/tables")
    public Map<String, Object> getPagedServiceSales(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "1") int scrollStep,
            @RequestParam(required = false) Long empNum,
            @RequestParam(required = false) Long memNum,
            @RequestParam(required = false) List<Long> serviceIds,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
    ) {
        return salesServiceService.getPagedServiceSales(
                keyword, page, scrollStep, empNum, memNum, serviceIds, startDate, endDate
        );
    }

    // 서비스 매출 그래프
    @GetMapping("/sales/service/graphs")
    public List<Map<String, Object>> getServiceSalesGraph(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) List<Long> serviceIds,
            @RequestParam(required = false) Long memNum,
            @RequestParam(required = false) Long empNum
    ) {
        return salesServiceService.getServiceSalesGraph(startDate, endDate, serviceIds, memNum, empNum);
    }
}
