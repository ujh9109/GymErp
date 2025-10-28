package com.example.gymerp.service;

import java.util.List;
import com.example.gymerp.dto.SalesService;

public interface SalesServiceService {

    // 서비스 판매 내역 전체 조회
    List<SalesService> getAllSalesServices();

    // 서비스 판매 내역 단일 조회
    SalesService getSalesServiceById(Long serviceSalesId);

    // 서비스 판매 내역 등록
    int addSalesService(SalesService salesService);

    // 서비스 판매 내역 수정
    int updateSalesService(SalesService salesService);

    // 서비스 판매 내역 삭제 (status = 'DELETED')
    int deleteSalesService(Long serviceSalesId);
}
