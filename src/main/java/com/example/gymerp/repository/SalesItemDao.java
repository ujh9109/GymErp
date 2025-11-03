package com.example.gymerp.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param; // MyBatis에서 Map 파라미터 외 단일 파라미터에 이름을 부여할 때 유용

import com.example.gymerp.dto.SalesItemDto;

@Mapper
public interface SalesItemDao {

    // (기존 메서드들)

    // 전체 판매 내역 조회 (페이징/필터링 파라미터 포함)
    List<SalesItemDto> selectAllSalesItems(Map<String, Object> params);

    // 전체 개수 조회 (페이징을 위해 필수)
    int selectSalesItemCount(Map<String, Object> params);

    // 단일 판매 내역 조회
    SalesItemDto selectSalesItemById(@Param("itemSalesId") Long itemSalesId);

    // ========== [재고/수정 관련 추가 메서드] ==========

    /**
     * 판매 내역 수정 및 삭제 시, 기존 수량(oldQuantity)과 상품 정보(productId, codeBId)를 조회합니다.
     * @param itemSalesId 판매 내역 ID
     * @return Map (productId, oldQuantity, codeBId, status 포함)
     */
    Map<String, Object> selectSalesItemForAdjustment(@Param("itemSalesId") Long itemSalesId); 

    /**
     * 재고 환원 (입고) 내역을 Purchase 테이블에 기록합니다. (판매 취소/수량 감소 시 사용)
     * @param params (productId, codeBId, quantity)
     */
    void insertPurchaseForRefund(Map<String, Object> params);
    
    // ============================================

    // 판매 내역 등록
    int insertSalesItem(SalesItemDto salesItem);

    // 판매 내역 수정
    int updateSalesItem(SalesItemDto salesItem);

    // 판매 내역 삭제 (status = 'DELETED')
    // MyBatis가 단일 Long 값을 처리하도록 @Param 사용을 권장합니다.
    int deleteSalesItem(@Param("itemSalesId") Long itemSalesId);

    // (통계 메서드들)

    // 상품 매출 통계 조회
    List<Map<String, Object>> selectItemSalesAnalytics(Map<String, Object> params);

    
}