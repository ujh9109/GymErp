package com.example.gymerp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.gymerp.service.SalesItemService;
import com.example.gymerp.service.SalesServiceService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class SalesController {

    private final SalesServiceService salesServiceService;
    private final SalesItemService salesItemService;

    /* ================================
       [서비스 판매 내역 조회]
    ================================ */

    // 서비스 판매 내역 조회 (페이지 단위 / 스크롤)
    @GetMapping("/sales/services/paged")
    public Map<String, Object> getPagedSales(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "1") int scrollStep,
            @RequestParam(required = false) Long empNum,
            @RequestParam(required = false) Long memNum,
            @RequestParam(required = false) List<Long> serviceIds,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {

        return salesServiceService.getPagedServiceSales(
                page, scrollStep, empNum, memNum, serviceIds, startDate, endDate
        );
    }

    /* ================================
       [서비스 매출 그래프 조회]
    ================================ */

    // 서비스 매출 그래프 조회
    @GetMapping("/sales/services/graph")
    public List<Map<String, Object>> getSalesGraph(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) List<Long> serviceIds,
            @RequestParam(required = false) Long memNum,
            @RequestParam(required = false) Long empNum) {

        return salesServiceService.getServiceSalesGraph(
                startDate, endDate, serviceIds, memNum, empNum
        );
    }

    /* ================================
       [직원(트레이너) 실적 조회]
    ================================ */

    // 트레이너 실적 리스트 조회 (페이징 + 스크롤)
    @GetMapping("/sales/trainer/performance/list")
    public Map<String, Object> getTrainerPerformanceList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "1") int scrollStep,
            @RequestParam(required = false) List<Long> empNums,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {

        return salesServiceService.getTrainerPerformanceList(
                page, scrollStep, empNums, startDate, endDate
        );
    }

    // 트레이너 실적 그래프 조회
    @GetMapping("/sales/trainer/performance/graph")
    public List<Map<String, Object>> getTrainerPerformanceGraph(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) List<Long> empNums,
            @RequestParam(required = false) String periodType) {

        return salesServiceService.getTrainerPerformanceGraph(
                startDate, endDate, empNums, periodType
        );
    }
    
    /* ================================
    [상품 매출 그래프 조회]
 ================================ */

	 @GetMapping("/sales/products/graph")
	 public ResponseEntity<Map<String, List<Map<String, Object>>>> getItemSalesGraph(
	         @RequestParam(required = false) String startDate,
	         @RequestParam(required = false) String endDate,
	         @RequestParam(defaultValue = "DAY") String groupByUnit) {
	     try {
	         Map<String, List<Map<String, Object>>> graphData =
	                 salesItemService.getItemSalesGraphData(startDate, endDate, groupByUnit);
	         return ResponseEntity.ok(graphData);
	     } catch (Exception e) {
	         e.printStackTrace();
	         
	         return ResponseEntity.status(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	     }
	 }    
}
