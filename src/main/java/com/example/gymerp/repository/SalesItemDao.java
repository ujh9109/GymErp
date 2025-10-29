package com.example.gymerp.repository;

import java.util.List;
import java.util.Map;

import com.example.gymerp.dto.SalesItem;

public interface SalesItemDao {
	// 전체 판매 내역 조회
    List<SalesItem> selectAllSalesItems();
    
    // 단일 판매 내역 조회 (필요 시)
    SalesItem selectSalesItemById(Long itemSalesId);
    
    // 판매 내역 등록
    int insertSalesItem(SalesItem salesItem);
    
    // 상품 판매 내역 수정
    int updateSalesItem(SalesItem salesItem);

    // 상품 판매 내역 삭제 (status = 'DELETED')
    int deleteSalesItem(Long itemSalesId);
    
	 // 상품 매출 통계 조회
	 // 파라미터를 Map으로 받아 SQL에 전달하도록 준비합니다.
	 List<Map<String, Object>> selectItemSalesAnalytics(Map<String, Object> params);
	
	 // 상품 매출 그래프 데이터 조회
	 // 파라미터를 Map으로 받아 SQL에 전달하도록 준비합니다.
	 List<Map<String, Object>> selectItemSalesGraphData(Map<String, Object> params);
}
