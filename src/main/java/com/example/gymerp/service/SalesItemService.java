package com.example.gymerp.service;

import java.util.List;
import java.util.Map;
// import org.springframework.transaction.annotation.Transactional; // Service 구현체에 적용 예정

import com.example.gymerp.dto.SalesItemDto;

public interface SalesItemService {

    // 전체 상품 판매 내역 조회 (페이징/필터링 적용)
    // 반환 타입: Map<String, Object> (목록과 전체 개수를 포함)
    Map<String, Object> getAllSalesItems(String startDate, String endDate, List<Long> itemIds, Long empNum, int page, int size); 

    // 단일 상품 판매 내역 조회
    SalesItemDto getSalesItemById(Long itemSalesId);

    /**
     * 상품 판매 내역을 등록하고, Sales_Item 테이블에 수량을 기록합니다.
     */
    int addSalesItem(SalesItemDto salesItem);
    
    /**
     * [재고 조정 로직 포함] 상품 판매 내역을 수정합니다.
     * 1. 기존 수량보다 감소된 경우: 감소분만큼 Purchase(입고) 테이블에 재고 환원 내역을 기록합니다.
     * 2. 기존 수량보다 증가된 경우: Sales_Item 테이블만 업데이트하며 재고 기록을 남기지 않습니다. (재고는 합산으로 계산됨)
     * 이 메서드는 Service 구현체에서 트랜잭션으로 처리되어야 합니다.
     */
    int updateSalesItem(SalesItemDto salesItem);

    /**
     * [재고 조정 로직 포함] 상품 판매 내역을 'DELETED' 상태로 소프트 삭제하고,
     * 삭제된 판매 수량 전체를 Purchase(입고) 테이블에 재고 환원 내역으로 기록합니다.
     * 이 메서드는 Service 구현체에서 트랜잭션으로 처리되어야 합니다.
     */
    int deleteSalesItem(Long itemSalesId);

	List<Map<String, Object>> getItemSalesAnalytics(String startDate, String endDate, List<Long> itemIds, Long memNum,
			Long empNum);

	Map<String, List<Map<String, Object>>> getItemSalesGraphData(String startDate, String endDate, String groupByUnit);
}