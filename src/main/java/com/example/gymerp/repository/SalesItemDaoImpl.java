package com.example.gymerp.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.example.gymerp.dto.SalesItemDto;

import lombok.RequiredArgsConstructor;

@Primary
@Repository
@RequiredArgsConstructor
public class SalesItemDaoImpl implements SalesItemDao {

    private final SqlSession session;
    // 🚨 MyBatis Mapper XML의 namespace와 일치하는 변수를 "SalesItemMapper"로 수정했습니다.
    // 이는 XML 파일의 <mapper namespace="SalesItemMapper">와 일치해야 합니다.
    private static final String NAMESPACE = "SalesItemMapper";

    // 1. 전체 상품 판매 내역 조회 (페이징/필터 포함)
    @Override
    public List<SalesItemDto> selectAllSalesItems(Map<String, Object> params) {
        return session.selectList(NAMESPACE + ".selectAllSalesItems", params);
    }

    // 2. 전체 개수 조회 (페이징 total)
    @Override
    public int selectSalesItemCount(Map<String, Object> params) {
        return session.selectOne(NAMESPACE + ".selectSalesItemCount", params);
    }

    // 3. 단일 상품 판매 내역 조회
    @Override
    public SalesItemDto selectSalesItemById(Long itemSalesId) {
        return session.selectOne(NAMESPACE + ".selectSalesItemById", itemSalesId);
    }
    
    // ========== [재고/수정 관련 추가 구현] ==========
    
    /**
     * 판매 내역 수정 및 삭제 시, 기존 수량(oldQuantity)과 상품 정보(productId, codeBId)를 조회합니다.
     */
    @Override
    public Map<String, Object> selectSalesItemForAdjustment(Long itemSalesId) {
        return session.selectOne(NAMESPACE + ".selectSalesItemForAdjustment", itemSalesId);
    }

    /**
     * 재고 환원 (입고) 내역을 Purchase 테이블에 기록합니다. (판매 취소/수량 감소 시 사용)
     */
    @Override
    public void insertPurchaseForRefund(Map<String, Object> params) {
        session.insert(NAMESPACE + ".insertPurchaseForRefund", params);
    }
    
    // ============================================


    // 4. 상품 판매 내역 등록
    @Override
    public int insertSalesItem(SalesItemDto salesItem) {
        // 이제 "SalesItemMapper.insertSalesItem" 쿼리를 찾게 됩니다.
        return session.insert(NAMESPACE + ".insertSalesItem", salesItem);
    }

    // 5. 상품 판매 내역 수정
    @Override
    public int updateSalesItem(SalesItemDto salesItem) {
        return session.update(NAMESPACE + ".updateSalesItem", salesItem);
    }

    // 6. 상품 판매 내역 삭제 (status = 'DELETED')
    @Override
    public int deleteSalesItem(Long itemSalesId) {
        return session.update(NAMESPACE + ".deleteSalesItem", itemSalesId);
    }

    // 7. 상품 매출 통계 조회
    @Override
    public List<Map<String, Object>> selectItemSalesAnalytics(Map<String, Object> params) {
        return session.selectList(NAMESPACE + ".selectItemSalesAnalytics", params);
    }

    // 8. 상품 매출 그래프 데이터 조회
    @Override
    public List<Map<String, Object>> selectItemSalesGraphData(Map<String, Object> params) {
        return session.selectList(NAMESPACE + ".selectItemSalesGraphData", params);
    }
}