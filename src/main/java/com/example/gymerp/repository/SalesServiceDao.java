package com.example.gymerp.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.example.gymerp.dto.SalesService;

@Mapper
public interface SalesServiceDao {

    // ===============================
    // [조회]
    // ===============================

    // 전체 서비스 판매 내역 조회
    List<SalesService> selectAllSalesServices();

    // 단일 서비스 판매 내역 조회
    SalesService selectSalesServiceById(long serviceSalesId);


    // ===============================
    // [등록 / 수정 / 삭제]
    // ===============================

    // 서비스 판매 등록
    int insertSalesService(SalesService salesService);

    // 서비스 판매 수정
    int updateSalesService(SalesService salesService);

    // 서비스 판매 삭제 (논리 삭제)
    int deleteSalesService(long serviceSalesId);

    // PT_LOG 생성 후 refundId 연동
    int updateRefundIdBySalesId(Map<String, Object> param);
}
