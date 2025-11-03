package com.example.gymerp.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
       [서비스 매출 그래프]
       - 필터: 기간, 품목, 회원, 직원
       - 품목 미선택 시 serviceType 기준 그룹화
       - 기간 미선택 시: 올해 전체 자동 설정
    ========================================================= */
    @Override
    public List<Map<String, Object>> getServiceSalesChart(
            String startDate,
            String endDate,
            List<String> serviceTypes,
            Long memberId,
            Long empId) {

        // 기간 미선택 시 올해 전체
        if (startDate == null || endDate == null || startDate.isEmpty() || endDate.isEmpty()) {
            int year = LocalDate.now().getYear();
            startDate = year + "-01-01";
            endDate = year + "-12-31";
        }

        Map<String, Object> params = new HashMap<>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("serviceTypes", serviceTypes);
        params.put("memberId", memberId);
        params.put("empId", empId);

        return salesAnalyticsDao.selectServiceSalesChart(params);
    }


    /* =========================================================
       [실물 상품 매출 그래프]
       - 필터: 기간, 품목, 직원
       - 품목 미선택 시 productType 기준 그룹화
       - 기간 미선택 시: 올해 전체 자동 설정
    ========================================================= */
    @Override
    public List<Map<String, Object>> getItemSalesChart(
            String startDate,
            String endDate,
            List<String> productTypes,
            Long empId) {

        // 기간 미선택 시 올해 전체
        if (startDate == null || endDate == null || startDate.isEmpty() || endDate.isEmpty()) {
            int year = LocalDate.now().getYear();
            startDate = year + "-01-01";
            endDate = year + "-12-31";
        }

        Map<String, Object> params = new HashMap<>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("productTypes", productTypes);
        params.put("empId", empId);

        return salesAnalyticsDao.selectItemSalesChart(params);
    }


    /* =========================================================
       [트레이너 실적 그래프]
       - 기준: PT 이용내역(status='소비')
       - 필터: 기간, 직원(최대 3명)
       - 기간 미선택 시: 저번 달 전체
       - 직원 미선택 시: 실적 TOP3 자동 조회
       - 기간 단위 자동 분기: 일 / 주 / 월 / 년
    ========================================================= */
    @Override
    public List<Map<String, Object>> getTrainerPerformanceChart(
            String startDate,
            String endDate,
            List<Long> trainerIds) {

        Map<String, Object> params = new HashMap<>();

        // 1️⃣ 기간 미선택 시: 저번 달로 자동 설정
        if (startDate == null || endDate == null || startDate.isEmpty() || endDate.isEmpty()) {
            LocalDate now = LocalDate.now();
            LocalDate firstDayOfLastMonth = now.minusMonths(1).withDayOfMonth(1);
            LocalDate lastDayOfLastMonth = firstDayOfLastMonth.withDayOfMonth(firstDayOfLastMonth.lengthOfMonth());
            startDate = firstDayOfLastMonth.toString();
            endDate = lastDayOfLastMonth.toString();
        }

        params.put("startDate", startDate);
        params.put("endDate", endDate);

        // 2️⃣ 직원 미선택 시: TOP3 조회
        if (trainerIds == null || trainerIds.isEmpty()) {
            List<Map<String, Object>> topTrainers = getTop3Trainers(startDate, endDate);
            trainerIds = topTrainers.stream()
                    .map(t -> ((Number) t.get("trainer_id")).longValue())
                    .toList();
        }
        params.put("trainerIds", trainerIds);

        // 3️⃣ 자동 단위 분기 계산
        String groupUnit = getGroupUnit(startDate, endDate);
        params.put("groupUnit", groupUnit);

        // 4️⃣ DAO 호출
        return salesAnalyticsDao.selectTrainerPerformanceChart(params);
    }


    /* =========================================================
       [AI 회원수 예측 그래프]
       - Flask 서버 호출 → 예측 결과 수신 → DB 저장 → 조회 반환
       - 기준: 올해 전체 (1월~12월)
       - 그래프: 월별 막대 그래프 (BarChart)
    ========================================================= */
    @Override
    public List<Map<String, Object>> getAiMemberPredictionChart() {
        String flaskUrl = "http://localhost:5000/predict/members";
        int currentYear = LocalDate.now().getYear();

        String startDate = currentYear + "-01-01";
        String endDate = currentYear + "-12-31";

        Map<String, Object> requestData = Map.of("startDate", startDate, "endDate", endDate);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(flaskUrl, requestData, Map.class);
            Map<String, Object> body = response.getBody();

            if (body == null || !body.containsKey("predictions")) {
                throw new IllegalStateException("Flask 서버 응답이 비정상입니다.");
            }

            List<Map<String, Object>> predictions = (List<Map<String, Object>>) body.get("predictions");
            for (Map<String, Object> item : predictions) {
                Map<String, Object> insertData = new HashMap<>();
                insertData.put("predictDate", item.get("date"));
                insertData.put("predictedCount", item.get("predicted_members"));
                salesAnalyticsDao.insertAiMemberPrediction(insertData);
            }

            return salesAnalyticsDao.selectAiMemberPredictions();

        } catch (Exception e) {
            throw new RuntimeException("AI 서버 호출 실패: " + e.getMessage(), e);
        }
    }


    /* =========================================================
       [AI 매출 예측 그래프]
       - 기준: 과거 3년치 실제 매출
       - Flask 호출 → 예측 결과 DB 저장 → 조회
       - 출력: 작년 / 올해 / 내년
       - 그래프: 꺾은선 그래프 (LineChart)
    ========================================================= */
    @Override
    public List<Map<String, Object>> getAiSalesPredictionChart() {
        String flaskUrl = "http://localhost:5000/predict/sales";

        try {
            // 1️⃣ 최근 3년치 매출 데이터 조회
            List<Map<String, Object>> pastSales = salesAnalyticsDao.selectPastSalesForAi();
            List<Double> monthlySales = pastSales.stream()
                    .map(row -> ((Number) row.get("total_sales")).doubleValue())
                    .toList();

            // 2️⃣ Flask 요청 데이터
            Map<String, Object> requestData = Map.of("monthly_sales", monthlySales);

            // 3️⃣ Flask 호출
            ResponseEntity<Map> response = restTemplate.postForEntity(flaskUrl, requestData, Map.class);
            Map<String, Object> body = response.getBody();

            if (body == null || !body.containsKey("predictions")) {
                throw new IllegalStateException("Flask 서버 응답이 비정상입니다.");
            }

            // 4️⃣ 예측 결과 DB 저장
            List<Map<String, Object>> predictions = (List<Map<String, Object>>) body.get("predictions");
            for (Map<String, Object> p : predictions) {
                Map<String, Object> insertData = new HashMap<>();
                insertData.put("predictYear", LocalDate.now().getYear() + 1);
                insertData.put("predictMonth", p.get("month"));
                insertData.put("predictedSales", p.get("predicted_sales"));
                salesAnalyticsDao.insertAiSalesPrediction(insertData);
            }

            // 5️⃣ 작년 / 올해 / 내년 데이터 조회 후 반환
            return salesAnalyticsDao.selectAiSalesPredictions();

        } catch (Exception e) {
            throw new RuntimeException("AI 매출 예측 실패: " + e.getMessage(), e);
        }
    }


    /* =========================================================
       [보조 메서드] 실적 TOP3 트레이너 조회
    ========================================================= */
    private List<Map<String, Object>> getTop3Trainers(String startDate, String endDate) {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        return salesAnalyticsDao.selectTop3TrainerPerformance(params);
    }

    /* =========================================================
       [공통 메서드] 기간 단위 자동 판별
    ========================================================= */
    private String getGroupUnit(String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        long days = ChronoUnit.DAYS.between(start, end);

        if (days <= 7) return "DAY";
        else if (days <= 30) return "WEEK";
        else if (days <= 365) return "MONTH";
        else return "YEAR";
    }
}
