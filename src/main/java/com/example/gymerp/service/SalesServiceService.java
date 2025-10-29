package com.example.gymerp.service;

import java.util.List;
import java.util.Map;

import com.example.gymerp.dto.SalesService;

public interface SalesServiceService {

	 // 전체 서비스 판매 내역 조회
    List<SalesService> getAllSalesServices();

    // 단일 서비스 판매 내역 조회
    SalesService getSalesServiceById(Long serviceSalesId);

    // 서비스 판매 등록
    int createSalesService(SalesService salesService);

    // 서비스 판매 수정
    int updateSalesService(SalesService salesService);

    // 서비스 판매 삭제
    int deleteSalesService(Long serviceSalesId);

    // 서비스 매출 통계 조회
    List<Map<String, Object>> getServiceSalesAnalytics(String startDate, String endDate, List<Long> serviceIds, Long memNum, Long empNum);

    // 서비스 매출 그래프 조회
    List<Map<String, Object>> getServiceSalesGraph(String startDate, String endDate, List<Long> serviceIds, Long memNum, Long empNum);
    
}
