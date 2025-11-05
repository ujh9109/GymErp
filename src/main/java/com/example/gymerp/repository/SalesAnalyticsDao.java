package com.example.gymerp.repository;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SalesAnalyticsDao {

    /* =========================================================
       [전체 매출 그래프]
       - Mapper: selectTotalSalesChart
       - 기준: 서비스 + 실물 상품 매출 합산
       - 필터: 기간, 품목(중분류)
       - 기본 출력: group_label(기간 단위), total_sales
       - 모든 필터 미선택 시: 올해 전체 자동 적용
    ========================================================= */
    List<Map<String, Object>> selectTotalSalesChart(Map<String, Object> params);


    /* =========================================================
       [서비스 매출 그래프]
       - Mapper: selectServiceSalesChart
       - 기준: sales_service
       - 필터: 기간, 품목(최대 3개)
       - 기본 출력: label(서비스명 or 타입), total_sales
       - 기간 미선택 시: 올해 전체 자동 적용
       - 품목 미선택 시: 중분류 전체
    ========================================================= */
    List<Map<String, Object>> selectServiceSalesChart(Map<String, Object> params);


    /* =========================================================
       [실물 상품 매출 그래프]
       - Mapper: selectItemSalesChart
       - 기준: sales_item
       - 필터: 기간, 품목(최대 3개)
       - 기본 출력: label(상품명 or 타입), total_sales
       - 기간 미선택 시: 올해 전체 자동 적용
       - 품목 미선택 시: 중분류 전체
    ========================================================= */
    List<Map<String, Object>> selectItemSalesChart(Map<String, Object> params);


    /* =========================================================
       [트레이너 실적 그래프]
       - Mapper: selectTrainerPerformanceChart
       - 기준: pt_log (status='소비')
       - 필터: 기간, 직원(최대 3명)
       - 기간 미선택 시: 저번달 전체 자동 적용
       - 직원 미선택 시: 실적 상위 3명 자동 선택
       - 출력: group_date, trainer_name, total_sessions
    ========================================================= */
    List<Map<String, Object>> selectTrainerPerformanceChart(Map<String, Object> params);


    /* =========================================================
       [AI 회원수 예측 결과 저장]
       - Mapper: insertAiMemberPrediction
       - Flask 예측 결과를 DB에 저장
       - 테이블: ai_member_prediction
    ========================================================= */
    int insertAiMemberPrediction(Map<String, Object> data);


    /* =========================================================
       [AI 회원수 예측 결과 조회]
       - Mapper: selectAiMemberPredictions
       - 기준: 올해(YYYY)
       - 출력: month, predictedCount
    ========================================================= */
    List<Map<String, Object>> selectAiMemberPredictions();


    /* =========================================================
       [AI 매출 예측용 과거 데이터 조회]
       - Mapper: selectPastSalesForAi
       - 기준: 최근 3년치 (서비스 + 실물 상품)
       - 출력: year, month, total_sales
       - Flask 학습용 데이터로만 사용 (그래프 출력 X)
    ========================================================= */
    List<Map<String, Object>> selectPastSalesForAi();


    /* =========================================================
       [AI 매출 예측 결과 저장]
       - Mapper: insertAiSalesPrediction
       - Flask 예측 결과를 DB에 저장
       - 테이블: ai_sales_prediction
    ========================================================= */
    int insertAiSalesPrediction(Map<String, Object> data);


    /* =========================================================
       [AI 매출 예측 결과 조회]
       - Mapper: selectAiSalesPredictions
       - 기준: 작년 / 올해 / 내년
       - 출력: year, month, predictedSales
    ========================================================= */
    List<Map<String, Object>> selectAiSalesPredictions();


    /* =========================================================
       [AI 회원 예측용 월별 회원 수 집계]
       - Mapper: selectMemberMonthlyCount
       - 대상: MEMBER 테이블
       - 역할:
           · created_at 기준 월별 신규 회원 수 집계
           · Flask /predict/members 요청용 데이터 생성
       - 출력: month, member_count
    ========================================================= */
    List<Map<String, Object>> selectMemberMonthlyCount(Map<String, Object> params);
}
