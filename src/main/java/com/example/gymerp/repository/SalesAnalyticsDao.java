package com.example.gymerp.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SalesAnalyticsDao {

    /* =========================================================
       [서비스 매출 그래프]
       - Mapper: selectServiceSalesChart
       - 필터: 기간, 품목(최대 3개), 회원, 직원
       - 기본 출력: label(품목명 or 타입), total_sales
       - 모든 필터 미선택 시: 올해 전체 자동 적용
    ========================================================= */
    List<Map<String, Object>> selectServiceSalesChart(Map<String, Object> params);


    /* =========================================================
       [실물 상품 매출 그래프]
       - Mapper: selectItemSalesChart
       - 필터: 기간, 품목(최대 3개), 직원
       - 기본 출력: label(상품명 or 타입), total_sales
       - 모든 필터 미선택 시: 올해 전체 자동 적용
    ========================================================= */
    List<Map<String, Object>> selectItemSalesChart(Map<String, Object> params);
    
    
    /* =========================================================
	    [트레이너 실적 그래프]
	    - Mapper: selectTrainerPerformanceChart
	    - 기준: PT 이용내역 (status = '소비')
	    - 필터: 기간, 직원
	    - 직원 미선택 시: 실적 TOP3
	    - 기간 미선택 시: 저번 달
	 ========================================================= */
	 List<Map<String, Object>> selectTrainerPerformanceChart(Map<String, Object> params);
	 
	 
	 /* =========================================================
	     [트레이너 실적 TOP3 조회]
	     - Mapper: selectTop3TrainerPerformance
	     - 기간 내 PT 소비 횟수 상위 3명
	     - 기준: pt_log (status='소비')
	  ========================================================= */
	  List<Map<String, Object>> selectTop3TrainerPerformance(Map<String, Object> params);
	  
	  
	  /* =========================================================
	      [AI 회원수 예측 결과 저장]
	      - Mapper: insertAiMemberPrediction
	      - Flask 예측 결과를 DB에 기록
	      - 테이블: ai_member_prediction
	   ========================================================= */
	   int insertAiMemberPrediction(Map<String, Object> data);
	
	
	   /* =========================================================
	      [AI 회원수 예측 결과 조회]
	      - Mapper: selectAiMemberPredictions
	      - 기준: 올해(YYYY)
	      - 출력: 월(month), 예측 회원수(predictedCount)
	   ========================================================= */
	   List<Map<String, Object>> selectAiMemberPredictions();


	   /* =========================================================
	      [AI 매출 예측용 과거 데이터 조회]
	      - Mapper: selectPastSalesForAi
	      - 기준: 최근 3년치 매출 데이터 (서비스 + 상품)
	      - 출력: 연도(year), 월(month), 매출액(total_sales)
	   ========================================================= */
	   List<Map<String, Object>> selectPastSalesForAi();


	   /* =========================================================
	      [AI 매출 예측 결과 저장]
	      - Mapper: insertAiSalesPrediction
	      - Flask에서 예측된 매출 데이터를 DB에 기록
	      - 테이블: ai_sales_prediction
	   ========================================================= */
	   int insertAiSalesPrediction(Map<String, Object> data);


	   /* =========================================================
	      [AI 매출 예측 결과 조회]
	      - Mapper: selectAiSalesPredictions
	      - 기준: 작년 / 올해 / 내년
	      - 출력: 연도(year), 월(month), 예측 매출(predictedSales)
	   ========================================================= */
	   List<Map<String, Object>> selectAiSalesPredictions();
}
