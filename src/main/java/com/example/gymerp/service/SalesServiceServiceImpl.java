package com.example.gymerp.service;

import java.time.LocalDate;
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

    // 서비스 판매 내역 등록
    @Override
    public int createSalesService(SalesService salesService) {
        return salesServiceDao.insertSalesService(salesService);
    }

    // 서비스 판매 내역 수정
    @Override
    public int updateSalesService(SalesService salesService) {
        return salesServiceDao.updateSalesService(salesService);
    }

    // 서비스 판매 내역 삭제 (status = 'DELETED')
    @Override
    public int deleteSalesService(Long serviceSalesId) {
        return salesServiceDao.deleteSalesService(serviceSalesId);
    }

    // 서비스 매출 통계 조회
    @Override
    public List<Map<String, Object>> getServiceSalesAnalytics(
            String startDate,
            String endDate,
            List<Long> serviceIds,
            Long memNum,
            Long empNum
    ) {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("serviceIds", serviceIds);
        params.put("memNum", memNum);
        params.put("empNum", empNum);

        // 기간 단위 자동 계산
        String periodType = "DAY";
        if (startDate != null && endDate != null) {
            long diff = ChronoUnit.DAYS.between(LocalDate.parse(startDate), LocalDate.parse(endDate));
            if (diff > 365) periodType = "YEAR";
            else if (diff > 30) periodType = "MONTH";
            else if (diff > 7) periodType = "WEEK";
        }
        params.put("periodType", periodType);

        return salesServiceDao.selectServiceSalesAnalytics(params);
    }
    
    
}
