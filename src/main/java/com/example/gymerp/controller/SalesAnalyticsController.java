package com.example.gymerp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.example.gymerp.service.SalesAnalyticsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class SalesAnalyticsController {

    private final SalesAnalyticsService salesAnalyticsService;

    /* =========================================================
       [서비스 매출 그래프]
       - 필터: 기간, 품목, 회원, 직원
       - 기간 미선택 시: 올해 전체
       - 품목 최대 3개까지 OR 조건
       - 그래프: 원형(PieChart) or 막대(BarChart)
    ========================================================= */
    @GetMapping("/analytics/sales/service")
    public List<Map<String, Object>> getServiceSalesChart(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) List<String> serviceTypes,
            @RequestParam(required = false) Long memberId,
            @RequestParam(required = false) Long empId
    ) {
        return salesAnalyticsService.getServiceSalesChart(startDate, endDate, serviceTypes, memberId, empId);
    }


    /* =========================================================
       [실물 상품 매출 그래프]
       - 필터: 기간, 품목, 직원
       - 기간 미선택 시: 올해 전체
       - 품목 최대 3개까지 OR 조건
       - 그래프: 원형(PieChart) or 막대(BarChart)
    ========================================================= */
    @GetMapping("/analytics/sales/item")
    public List<Map<String, Object>> getItemSalesChart(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) List<String> productTypes,
            @RequestParam(required = false) Long empId
    ) {
        return salesAnalyticsService.getItemSalesChart(startDate, endDate, productTypes, empId);
    }
    
    
    /* =========================================================
	    [트레이너 실적 그래프]
	    - 기준: PT 이용내역(status='소비')
	    - 필터: 기간, 직원(최대 3명)
	    - 기간 미선택 시: 저번 달 전체
	    - 직원 미선택 시: 실적 TOP3
	    - 단위 자동 분기: 일 / 주 / 월 / 년
	    - 그래프: 꺾은선(LineChart)
	 ========================================================= */
	 @GetMapping("/analytics/trainer/performance")
	 public List<Map<String, Object>> getTrainerPerformanceChart(
	         @RequestParam(required = false) String startDate,
	         @RequestParam(required = false) String endDate,
	         @RequestParam(required = false) List<Long> trainerIds
	 ) {
	     return salesAnalyticsService.getTrainerPerformanceChart(startDate, endDate, trainerIds);
	 }


    /* =========================================================
       [AI 회원수 예측 그래프]
       - Flask 서버 호출 → 예측 결과 수신 → DB 저장 후 조회 반환
       - 기준: 올해 전체 (1월~12월)
       - 테이블: ai_member_prediction
       - 그래프: 월별 막대 그래프 (BarChart)
       - 필터 없음 (자동 호출)
    ========================================================= */
    @GetMapping("/analytics/ai/members")
    public List<Map<String, Object>> getAiMemberPredictionChart() {
        return salesAnalyticsService.getAiMemberPredictionChart();
    }


    /* =========================================================
       [AI 매출 예측 그래프]
       - Flask 서버 호출 → 예측 결과 수신 → DB 저장 후 조회 반환
       - 기준: 작년 / 올해 / 내년
       - 테이블: ai_sales_prediction
       - 그래프: 꺾은선(LineChart)
       - 필터 없음 (자동 호출)
    ========================================================= */
    @GetMapping("/analytics/ai/sales")
    public List<Map<String, Object>> getAiSalesPredictionChart() {
        return salesAnalyticsService.getAiSalesPredictionChart();
    }
}
