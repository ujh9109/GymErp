package com.example.gymerp.repository;

import java.util.List;
import com.example.gymerp.dto.SalesService;

public interface SalesServiceDao {

    // 전체 서비스 판매 내역 조회
    List<SalesService> selectAllSalesServices();

    // 단일 서비스 판매 내역 조회
    SalesService selectSalesServiceById(Long serviceSalesId);

    // 서비스 판매 내역 추가
    int insertSalesService(SalesService salesService);
    
    // 서비스 판매 내역 수정
    int updateSalesService(SalesService salesService);

    // 서비스 판매 내역 삭제 (status = 'DELETED')
    int deleteSalesService(Long serviceSalesId);
}
