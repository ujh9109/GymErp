package com.example.gymerp.service;

import java.util.List;
import java.util.Map;

public interface SalesAnalyticsService {

    /* =========================================================
       [전체 매출 그래프]
       - 기준: 서비스 + 실물 상품 매출 합산
       - 필터: 기간, 품목(중분류)
       - 기간 미선택 시: 올해 전체
       - 품목 미선택 시: 전체 중분류
       - 결과: groupLabel, totalSales
    ========================================================= */
    List<Map<String, Object>> getTotalSalesChart(
            String startDate,
            String endDate,
            List<String> categories
    );


    /* =========================================================
       [서비스 매출 그래프]
       - 기준: sales_service
       - 필터: 기간, 품목(최대 3개)
       - 기간 미선택 시: 올해 전체
       - 품목 미선택 시: 중분류 전체
       - 결과: label, totalSales
    ========================================================= */
    List<Map<String, Object>> getServiceSalesChart(
            String startDate,
            String endDate,
            List<String> serviceTypes
    );


    /* =========================================================
       [실물 상품 매출 그래프]
       - 기준: sales_item
       - 필터: 기간, 품목(최대 3개)
       - 기간 미선택 시: 올해 전체
       - 품목 미선택 시: 중분류 전체
       - 결과: label, totalSales
    ========================================================= */
    List<Map<String, Object>> getItemSalesChart(
            String startDate,
            String endDate,
            List<String> productTypes
    );


    /* =========================================================
       [트레이너 실적 그래프]
       - 기준: PT 이용내역(status='소비')
       - 필터: 기간, 직원(최대 3명)
       - 기간 미선택 시: 저번 달 전체
       - 직원 미선택 시: 실적 TOP3 자동 선택
       - 결과: groupDate, trainerName, totalSessions
    ========================================================= */
    List<Map<String, Object>> getTrainerPerformanceChart(
            String startDate,
            String endDate,
            List<Long> trainerIds
    );


    /* =========================================================
       [AI 회원수 예측 그래프]
       - Flask 서버에서 받은 예측 데이터를 DB에 저장 후 조회
       - 기준: 올해 전체 (1월~12월)
       - 테이블: ai_member_prediction
       - 그래프: 월별 막대 그래프 (BarChart)
    ========================================================= */
    List<Map<String, Object>> getAiMemberPredictionChart();


    /* =========================================================
       [AI 매출 예측 그래프]
       - 기준: 과거 3년치 실제 매출(sales_item + sales_service)
       - Flask 서버에서 예측(ARIMA) 수행 후 결과 저장
       - 대상: 작년 / 올해 / 내년
       - 테이블: ai_sales_prediction
       - 그래프: 꺾은선 그래프 (LineChart)
    ========================================================= */
    List<Map<String, Object>> getAiSalesPredictionChart();
    
    
    
}
