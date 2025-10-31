package com.example.gymerp.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.example.gymerp.dto.SalesService;

@Mapper
public interface SalesServiceDao {

    // 전체 서비스 판매 내역 조회
    List<SalesService> selectAllSalesServices();

    // 단일 서비스 판매 내역 조회
    SalesService selectSalesServiceById(long serviceSalesId);

    // 서비스 판매 등록
    int insertSalesService(SalesService salesService);

    // 서비스 판매 수정
    int updateSalesService(SalesService salesService);

    // 서비스 판매 삭제 (논리 삭제)
    int deleteSalesService(long serviceSalesId);

    // 서비스 판매 내역 조회 (페이지네이션 + 필터)
    List<SalesService> selectPagedServiceSales(Map<String, Object> param);

    // 전체 행 개수 조회 (페이지네이션용)
    int countPagedServiceSales(Map<String, Object> param);

    // 서비스 매출 그래프 조회
    List<Map<String, Object>> selectServiceSalesGraph(Map<String, Object> param);
}
