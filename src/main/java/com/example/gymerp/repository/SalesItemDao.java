package com.example.gymerp.repository;

import java.util.List;
import java.util.Map;

import com.example.gymerp.dto.SalesItem;

public interface SalesItemDao {

    // 전체 판매 내역 조회 (페이징/필터링 파라미터 포함)
    List<SalesItem> selectAllSalesItems(Map<String, Object> params);

    // 전체 개수 조회 (페이징을 위해 필수)
    int selectSalesItemCount(Map<String, Object> params);

    // 단일 판매 내역 조회
    SalesItem selectSalesItemById(Long itemSalesId);

    // 판매 내역 등록
    int insertSalesItem(SalesItem salesItem);

    // 판매 내역 수정
    int updateSalesItem(SalesItem salesItem);

    // 판매 내역 삭제 (status = 'DELETED')
    int deleteSalesItem(Long itemSalesId);

    // 상품 매출 통계 조회
    List<Map<String, Object>> selectItemSalesAnalytics(Map<String, Object> params);

    // 상품 매출 그래프 데이터 조회
    List<Map<String, Object>> selectItemSalesGraphData(Map<String, Object> params);
}
