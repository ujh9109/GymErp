package com.example.gymerp.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gymerp.dto.SalesService;
import com.example.gymerp.repository.SalesServiceDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SalesServiceServiceImpl implements SalesServiceService {

    private final SalesServiceDao salesServiceDao;

    // 전체 서비스 판매 내역 조회
    @Override
    public List<SalesService> getAllSalesServices() {
        return salesServiceDao.selectAllSalesServices();
    }

    // 단일 서비스 판매 내역 조회
    @Override
    public SalesService getSalesServiceById(Long serviceSalesId) {
        return salesServiceDao.selectSalesServiceById(serviceSalesId);
    }

    // 서비스 판매 등록
    @Override
    public int createSalesService(SalesService salesService) {
        return salesServiceDao.insertSalesService(salesService);
    }

    // 서비스 판매 수정
    @Override
    public int updateSalesService(SalesService salesService) {
        return salesServiceDao.updateSalesService(salesService);
    }

    // 서비스 판매 삭제
    @Override
    public int deleteSalesService(Long serviceSalesId) {
        return salesServiceDao.deleteSalesService(serviceSalesId);
    }

    // 서비스 판매 내역 조회 (페이지당 20개 / 스크롤 10+10)
    @Override
    public Map<String, Object> getPagedServiceSales(String keyword, int page, int scrollStep,
                                                    Long empNum, Long memNum, List<Long> serviceIds,
                                                    String startDate, String endDate) {

        Map<String, Object> params = buildParams(startDate, endDate, serviceIds, memNum, empNum);
        params.put("keyword", keyword);

        int limit = 10;
        int offset = (page - 1) * 20 + (scrollStep - 1) * 10;
        params.put("offset", offset);
        params.put("limit", limit);

        List<SalesService> list = salesServiceDao.selectPagedServiceSales(params);
        int total = salesServiceDao.countPagedServiceSales(params);

        Map<String, Object> result = new HashMap<>();
        result.put("data", list);
        result.put("total", total);
        result.put("currentPage", page);
        result.put("scrollStep", scrollStep);
        result.put("pageSize", 20);
        result.put("hasNextPage", (page * 20) < total);
        result.put("hasMoreInPage", (scrollStep * 10) < Math.min(total - (page - 1) * 20, 20));
        return result;
    }

    // 서비스 매출 그래프 조회
    @Override
    public List<Map<String, Object>> getServiceSalesGraph(String startDate, String endDate,
                                                          List<Long> serviceIds, Long memNum, Long empNum) {
        Map<String, Object> params = buildParams(startDate, endDate, serviceIds, memNum, empNum);
        return salesServiceDao.selectServiceSalesGraph(params);
    }

    // 조회 조건 공통 파라미터 생성
    private Map<String, Object> buildParams(String startDate, String endDate,
                                            List<Long> serviceIds, Long memNum, Long empNum) {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("serviceIds", serviceIds);
        params.put("memNum", memNum);
        params.put("empNum", empNum);

        if (startDate != null && endDate != null) {
            LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
            LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);
            LocalDate today = LocalDate.now();

            if (start.isAfter(end)) throw new IllegalArgumentException("시작일은 마감일보다 뒤일 수 없습니다.");
            if (end.isAfter(today)) throw new IllegalArgumentException("마감일은 오늘 이후의 날짜를 지정할 수 없습니다.");

            long days = ChronoUnit.DAYS.between(start, end);
            String periodType = (days > 365) ? "YEAR"
                    : (days > 30) ? "MONTH"
                    : (days > 7) ? "WEEK" : "DAY";
            params.put("periodType", periodType);
        } else {
            params.put("periodType", "DAY");
        }

        return params;
    }
}
