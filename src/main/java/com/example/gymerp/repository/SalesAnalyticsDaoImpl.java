package com.example.gymerp.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Primary
@Repository
@RequiredArgsConstructor
public class SalesAnalyticsDaoImpl implements SalesAnalyticsDao {

    private final SqlSession sqlSession;


    /* =========================================================
       [서비스 매출 그래프]
       - Mapper ID: selectServiceSalesChart
       - 필터: 기간, 품목(최대 3개), 회원, 직원
       - 출력: label(품목명 or 서비스 타입), total_sales
       
       ✅ 필터별 동작 규칙
       ---------------------------------------------------------
       ▪ 모든 필터 미선택 시  
         → 올해 전체(1월~12월)의 모든 서비스 매출 합계를 조회  
         → 그룹화 기준: service_type

       ▪ 기간 미선택 시  
         → 전체 기간으로 간주 (DB 내 모든 매출 포함)

       ▪ 품목(serviceTypes) 미선택 시  
         → 서비스 타입(service_type) 기준으로 그룹화  
         → 예: PT, GX, 회원권 등 카테고리 단위 집계

       ▪ 품목 선택 시  
         → 선택된 service_type 목록만 OR 조건으로 필터링  
         → 그룹화 기준: service_name (실제 상품명 단위)

       ▪ 회원(memberId) 미선택 시  
         → 모든 회원의 매출 포함

       ▪ 직원(empId) 미선택 시  
         → 모든 직원(담당자)의 매출 포함
    ========================================================= */
    @Override
    public List<Map<String, Object>> selectServiceSalesChart(Map<String, Object> params) {
        return sqlSession.selectList("SalesAnalyticsDao.selectServiceSalesChart", params);
    }


    /* =========================================================
       [실물 상품 매출 그래프]
       - Mapper ID: selectItemSalesChart
       - 필터: 기간, 품목(최대 3개), 직원
       - 출력: label(상품명 or 상품 타입), total_sales

       ✅ 필터별 동작 규칙
       ---------------------------------------------------------
       ▪ 모든 필터 미선택 시  
         → 올해 전체(1월~12월)의 모든 실물 상품 매출 합계를 조회  
         → 그룹화 기준: product_type

       ▪ 기간 미선택 시  
         → 전체 기간으로 간주 (모든 판매 내역 포함)

       ▪ 품목(productTypes) 미선택 시  
         → 상품 타입(product_type) 기준으로 그룹화  
         → 예: 음료, 보충제, 의류 등 카테고리 단위 집계

       ▪ 품목 선택 시  
         → 선택된 product_type 목록만 OR 조건으로 필터링  
         → 그룹화 기준: product_name (실제 상품명 단위)

       ▪ 직원(empId) 미선택 시  
         → 모든 직원(담당자)의 매출 포함
    ========================================================= */
    @Override
    public List<Map<String, Object>> selectItemSalesChart(Map<String, Object> params) {
        return sqlSession.selectList("SalesAnalyticsDao.selectItemSalesChart", params);
    }
    
    
    /* =========================================================
	    [트레이너 실적 그래프]
	    - Mapper ID: selectTrainerPerformanceChart
	    - 기준: PT 이용내역 (status = '소비')
	    - 필터: 기간, 직원(최대 3명)
	    - 출력: group_date, trainer_name, total_sessions

	    ✅ 필터별 동작 규칙
	    ---------------------------------------------------------
	    ▪ 기간 미선택 시  
	      → 자동으로 “저번 달 전체 기간”으로 설정  
	      → 예: 2025년 3월 기준 → 2025년 2월 1일~2월 28일

	    ▪ 직원(trainerIds) 미선택 시  
	      → 해당 기간 동안 PT 이용량이 가장 많은 상위 3명의 트레이너 자동 선택

	    ▪ 단위(groupUnit) 자동 결정  
	      → 7일 이하 → DAY  
	      → 30일 이하 → WEEK  
	      → 365일 이하 → MONTH  
	      → 그 이상 → YEAR
	 ========================================================= */
	 @Override
	 public List<Map<String, Object>> selectTrainerPerformanceChart(Map<String, Object> params) {
	     return sqlSession.selectList("SalesAnalyticsDao.selectTrainerPerformanceChart", params);
	 }
	 
	 
	 /* =========================================================
	     [트레이너 실적 TOP3 조회]
	     - Mapper ID: selectTop3TrainerPerformance
	     - 기준: PT 이용내역(status='소비')
	     - 기간 내 소비 횟수 기준 상위 3명 조회
	  ========================================================= */
	  @Override
	  public List<Map<String, Object>> selectTop3TrainerPerformance(Map<String, Object> params) {
	      return sqlSession.selectList("SalesAnalyticsDao.selectTop3TrainerPerformance", params);
	  }
	  
	  
	  /* =========================================================
	      [AI 회원수 예측 결과 저장]
	      - Mapper ID: insertAiMemberPrediction
	      - Flask 서버에서 받은 예측 데이터를 DB에 기록
	      - 테이블: ai_member_prediction
	   ========================================================= */
	   @Override
	   public int insertAiMemberPrediction(Map<String, Object> data) {
	       return sqlSession.insert("SalesAnalyticsDao.insertAiMemberPrediction", data);
	   }

	   
	   /* =========================================================
	      [AI 회원수 예측 결과 조회]
	      - Mapper ID: selectAiMemberPredictions
	      - 기준: 올해(YYYY)
	      - 출력: 월(month), 예측 회원수(predictedCount)
	      - 그래프 유형: 선형(Line Chart)
	   ========================================================= */
	   @Override
	   public List<Map<String, Object>> selectAiMemberPredictions() {
	       return sqlSession.selectList("SalesAnalyticsDao.selectAiMemberPredictions");
	   }


	   /* =========================================================
	      [AI 매출 예측용 과거 데이터 조회]
	      - Mapper ID: selectPastSalesForAi
	      - 기준: 최근 3년치 서비스+상품 매출
	      - 출력: 연도(year), 월(month), 매출액(total_sales)
	      - Flask 학습용 데이터 제공
	   ========================================================= */
	   @Override
	   public List<Map<String, Object>> selectPastSalesForAi() {
	       return sqlSession.selectList("SalesAnalyticsDao.selectPastSalesForAi");
	   }


	   /* =========================================================
	      [AI 매출 예측 결과 저장]
	      - Mapper ID: insertAiSalesPrediction
	      - Flask 예측 결과를 DB에 기록
	      - 테이블: ai_sales_prediction
	   ========================================================= */
	   @Override
	   public int insertAiSalesPrediction(Map<String, Object> data) {
	       return sqlSession.insert("SalesAnalyticsDao.insertAiSalesPrediction", data);
	   }


	   /* =========================================================
	      [AI 매출 예측 결과 조회]
	      - Mapper ID: selectAiSalesPredictions
	      - 기준: 작년 / 올해 / 내년
	      - 출력: 연도(year), 월(month), 예측 매출(predictedSales)
	      - 그래프 타입: 꺾은선(Line Chart)
	   ========================================================= */
	   @Override
	   public List<Map<String, Object>> selectAiSalesPredictions() {
	       return sqlSession.selectList("SalesAnalyticsDao.selectAiSalesPredictions");
	   }
}
