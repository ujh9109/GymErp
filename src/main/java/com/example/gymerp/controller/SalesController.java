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
	
	private final com.example.gymerp.service.SalesItemService salesItemService;

 // 2. 상품 매출 통계 조회 (추가된 기능)
    // 요청: 상품 매출 통계
    @GetMapping("/sales/items/analytics")
    public List<Map<String, Object>> getItemSalesAnalytics(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) List<Long> itemIds, // 상품 ID 목록
            @RequestParam(required = false) Long memNum,
            @RequestParam(required = false) Long empNum
    ) {
        // SalesItemService의 구현체에 getItemSalesAnalytics 메서드가 필요합니다.
        return salesItemService.getItemSalesAnalytics(startDate, endDate, itemIds, memNum, empNum); 
    }

    // 3. 상품 매출 그래프 데이터 조회 (추가된 기능)
    // 요청: 매출 그래프 데이터 (상품)
    @GetMapping("/sales/items/graph/data")
    public Map<String, List<Map<String, Object>>> getItemSalesGraphData(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String groupByUnit // 'DAY', 'MONTH' 등 그룹화 단위
    ) {
        // SalesItemService의 구현체에 getItemSalesGraphData 메서드가 필요합니다.
        return salesItemService.getItemSalesGraphData(startDate, endDate, groupByUnit);
    }
    
}
