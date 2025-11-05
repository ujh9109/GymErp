package com.example.gymerp.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import com.example.gymerp.repository.SalesAnalyticsDao;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SalesAnalyticsServiceImpl implements SalesAnalyticsService {

    private final SalesAnalyticsDao salesAnalyticsDao;
    private final RestTemplate restTemplate = new RestTemplate();

    /* =========================================================
       [전체 매출 그래프]
       - 서비스 + 실물 상품 통합
       - 기본 카테고리 자동 세팅
    ========================================================= */
    @Override
    public List<Map<String, Object>> getTotalSalesChart(String startDate, String endDate, List<String> categories) {
        if (startDate == null || endDate == null || startDate.isEmpty() || endDate.isEmpty()) {
            int year = LocalDate.now().getYear();
            startDate = year + "-01-01";
            endDate = year + "-12-31";
        }

        if (categories == null || categories.isEmpty()) {
            categories = Arrays.asList("PT", "VOUCHER", "SUPPLEMENTS", "DRINK", "CLOTHES");
        }

        Map<String, Object> params = new HashMap<>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("categories", categories);
        params.put("groupUnit", getGroupUnit(startDate, endDate));

        List<Map<String, Object>> result = salesAnalyticsDao.selectTotalSalesChart(params);
        return fillMissingPeriods(result, startDate, endDate, params.get("groupUnit").toString());
    }

    /* =========================================================
       [서비스 매출 그래프]
    ========================================================= */
    @Override
    public List<Map<String, Object>> getServiceSalesChart(String startDate, String endDate, List<String> serviceTypes) {
        if (startDate == null || endDate == null || startDate.isEmpty() || endDate.isEmpty()) {
            int year = LocalDate.now().getYear();
            startDate = year + "-01-01";
            endDate = year + "-12-31";
        }

        Map<String, Object> params = new HashMap<>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("serviceTypes", serviceTypes);
        params.put("groupUnit", getGroupUnit(startDate, endDate));

        List<Map<String, Object>> result = salesAnalyticsDao.selectServiceSalesChart(params);
        // ✅ 변경된 부분
        return fillMissingPeriods(result, startDate, endDate, params.get("groupUnit").toString());
    }

    /* =========================================================
       [실물 상품 매출 그래프]
    ========================================================= */
    @Override
    public List<Map<String, Object>> getItemSalesChart(String startDate, String endDate, List<String> productTypes) {
        if (startDate == null || endDate == null || startDate.isEmpty() || endDate.isEmpty()) {
            int year = LocalDate.now().getYear();
            startDate = year + "-01-01";
            endDate = year + "-12-31";
        }

        Map<String, Object> params = new HashMap<>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("productTypes", productTypes);
        params.put("groupUnit", getGroupUnit(startDate, endDate));

        List<Map<String, Object>> result = salesAnalyticsDao.selectItemSalesChart(params);
        // ✅ 변경된 부분
        return fillMissingPeriods(result, startDate, endDate, params.get("groupUnit").toString());
    }

    /* =========================================================
       [트레이너 실적 그래프]
    ========================================================= */
    @Override
    public List<Map<String, Object>> getTrainerPerformanceChart(String startDate, String endDate, List<Long> trainerIds) {
        Map<String, Object> params = new HashMap<>();

        if (startDate == null || endDate == null || startDate.isEmpty() || endDate.isEmpty()) {
            LocalDate now = LocalDate.now();
            LocalDate firstDay = now.minusMonths(1).withDayOfMonth(1);
            LocalDate lastDay = firstDay.withDayOfMonth(firstDay.lengthOfMonth());
            startDate = firstDay.toString();
            endDate = lastDay.toString();
        }

        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("trainerIds", trainerIds);
        params.put("groupUnit", getGroupUnit(startDate, endDate));

        return salesAnalyticsDao.selectTrainerPerformanceChart(params);
    }

    /* =========================================================
	    [AI 회원수 예측 그래프] - DB 기반
	 ========================================================= */
	 @Override
	 public List<Map<String, Object>> getAiMemberPredictionChart() {
	     try {
	         List<Map<String, Object>> result = salesAnalyticsDao.selectAiMemberPredictions();
	         if (result == null || result.isEmpty()) {
	             throw new IllegalStateException("예측 데이터가 존재하지 않습니다. (ai_member_prediction 테이블 확인 필요)");
	         }
	         return result;
	     } catch (Exception e) {
	         throw new RuntimeException("AI 회원 예측 조회 실패: " + e.getMessage(), e);
	     }
	 }
	
	 /* =========================================================
	    [AI 매출 예측 그래프] - DB 기반
	 ========================================================= */
	 @Override
	 public List<Map<String, Object>> getAiSalesPredictionChart() {
	     try {
	         List<Map<String, Object>> result = salesAnalyticsDao.selectAiSalesPredictions();
	         if (result == null || result.isEmpty()) {
	             throw new IllegalStateException("예측 데이터가 존재하지 않습니다. (ai_sales_prediction 테이블 확인 필요)");
	         }
	         return result;
	     } catch (Exception e) {
	         throw new RuntimeException("AI 매출 예측 조회 실패: " + e.getMessage(), e);
	     }
	 }

    /* =========================================================
       [공통 메서드] 기간 단위 자동 판별
    ========================================================= */
    private String getGroupUnit(String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        long days = ChronoUnit.DAYS.between(start, end);
        if (days <= 7) return "DAY";
        if (days <= 30) return "WEEK";
        if (days <= 365) return "MONTH";
        return "YEAR";
    }

    /* =========================================================
       [공통 메서드] 누락 구간 보정
    ========================================================= */
    private List<Map<String, Object>> fillMissingPeriods(
            List<Map<String, Object>> result,
            String startDate,
            String endDate,
            String unit
    ) {
        Map<String, Map<String, Object>> map = new LinkedHashMap<>();
        for (Map<String, Object> row : result) {
            String label = String.valueOf(row.get("GROUP_LABEL"));
            map.put(label, row);
        }

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        while (!start.isAfter(end)) {
            String label;
            switch (unit) {
                case "DAY" -> label = start.toString();
                case "WEEK" -> label = start.getYear() + "-W" +
                        String.format("%02d", start.get(java.time.temporal.IsoFields.WEEK_OF_WEEK_BASED_YEAR));
                case "MONTH" -> label = start.toString().substring(0, 7);
                default -> label = String.valueOf(start.getYear());
            }

            map.putIfAbsent(label, Map.of("GROUP_LABEL", label, "TOTAL_SALES", 0));

            start = switch (unit) {
                case "DAY" -> start.plusDays(1);
                case "WEEK" -> start.plusWeeks(1);
                case "MONTH" -> start.plusMonths(1);
                default -> start.plusYears(1);
            };
        }

        return map.values().stream()
                .sorted(Comparator.comparing(m -> String.valueOf(m.get("GROUP_LABEL"))))
                .toList();
    }
}
