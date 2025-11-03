package com.example.gymerp.service;

import java.util.List;
import com.example.gymerp.dto.SalesService;

public interface SalesServiceService {

    /* ===============================
       [기본 CRUD]
    =============================== */

    // 전체 서비스 판매 내역 조회
    List<SalesService> getAllSalesServices();

    // 단일 서비스 판매 내역 조회
    SalesService getSalesServiceById(long serviceSalesId);

    // 서비스 판매 등록 (PT / 회원권 로그 포함, 트랜잭션)
    int createSalesService(SalesService salesService);

    // 서비스 판매 수정 (연장 / 부분환불 로직 포함, 트랜잭션)
    int updateSalesService(SalesService salesService);

    // 서비스 판매 삭제 (논리삭제 + 환불 처리 포함, 트랜잭션)
    int deleteSalesService(long serviceSalesId);
}
