package com.example.gymerp.service;

import java.util.List;
import java.util.Map;

import com.example.gymerp.dto.SalesItem;

public interface SalesItemService {

    // 전체 상품 판매 내역 조회
    List<SalesItem> getAllSalesItems();

    // 단일 상품 판매 내역 조회
    SalesItem getSalesItemById(Long itemSalesId);

    // 상품 판매 내역 등록
    int addSalesItem(SalesItem salesItem);
    
    // 상품 판매 내역 수정
    int updateSalesItem(SalesItem salesItem);

    // 상품 판매 내역 삭제 (status = 'DELETED')
    int deleteSalesItem(Long itemSalesId);

	List<Map<String, Object>> getItemSalesAnalytics(String startDate, String endDate, List<Long> itemIds, Long memNum,
			Long empNum);

	Map<String, List<Map<String, Object>>> getItemSalesGraphData(String startDate, String endDate, String groupByUnit);
}
