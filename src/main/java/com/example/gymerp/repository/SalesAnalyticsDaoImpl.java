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
       [전체 매출 그래프]
       - Mapper ID: selectTotalSalesChart
       - 기준: 서비스 + 실물 상품 매출 합산
       - 필터: 기간, 품목(중분류)
       - 모든 필터 미선택 시: 올해 전체 자동 적용
    ========================================================= */
    @Override
    public List<Map<String, Object>> selectTotalSalesChart(Map<String, Object> params) {
        return sqlSession.selectList("SalesAnalyticsDao.selectTotalSalesChart", params);
    }

    /* =========================================================
       [서비스 매출 그래프]
       - Mapper ID: selectServiceSalesChart
       - 기준: sales_service
       - 필터: 기간, 품목(최대 3개)
       - 기간 미선택 시: 올해 전체 자동 적용
       - 품목 미선택 시: 중분류 전체
    ========================================================= */
    @Override
    public List<Map<String, Object>> selectServiceSalesChart(Map<String, Object> params) {
        return sqlSession.selectList("SalesAnalyticsDao.selectServiceSalesChart", params);
    }

    /* =========================================================
       [실물 상품 매출 그래프]
       - Mapper ID: selectItemSalesChart
       - 기준: sales_item
       - 필터: 기간, 품목(최대 3개)
       - 기간 미선택 시: 올해 전체 자동 적용
       - 품목 미선택 시: 중분류 전체
    ========================================================= */
    @Override
    public List<Map<String, Object>> selectItemSalesChart(Map<String, Object> params) {
        return sqlSession.selectList("SalesAnalyticsDao.selectItemSalesChart", params);
    }

    /* =========================================================
       [트레이너 실적 그래프]
       - Mapper ID: selectTrainerPerformanceChart
       - 기준: pt_log (status='소비')
       - 필터: 기간, 직원(최대 3명)
       - 기간 미선택 시: 저번달 자동 적용
       - 직원 미선택 시: Top3 트레이너 자동 선택
    ========================================================= */
    @Override
    public List<Map<String, Object>> selectTrainerPerformanceChart(Map<String, Object> params) {
        return sqlSession.selectList("SalesAnalyticsDao.selectTrainerPerformanceChart", params);
    }

    /* =========================================================
       [AI 회원수 예측 결과 저장]
       - Mapper ID: insertAiMemberPrediction
       - Flask 예측 결과를 DB에 기록
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
       - Flask 학습용 데이터 제공 (그래프 출력 X)
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
    ========================================================= */
    @Override
    public List<Map<String, Object>> selectAiSalesPredictions() {
        return sqlSession.selectList("SalesAnalyticsDao.selectAiSalesPredictions");
    }

    /* =========================================================
       [AI 회원 예측용 월별 회원 수 집계]
       - Mapper ID: selectMemberMonthlyCount
       - 대상: MEMBER 테이블
       - 역할:
           · created_at 기준 월별 신규 회원 수 집계
           · Flask /predict/members 요청용 데이터 생성
       - 출력: month, member_count
    ========================================================= */
    @Override
    public List<Map<String, Object>> selectMemberMonthlyCount(Map<String, Object> params) {
        return sqlSession.selectList("SalesAnalyticsDao.selectMemberMonthlyCount", params);
    }
}
