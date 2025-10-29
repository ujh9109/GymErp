package com.example.gymerp.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.gymerp.dto.SalesItem;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SalesItemDaoImpl implements SalesItemDao {

    private final SqlSession session;

    // 1. 전체 상품 판매 내역 조회 (페이징/필터 포함)
    @Override
    public List<SalesItem> selectAllSalesItems(Map<String, Object> params) {
        return session.selectList("SalesItemMapper.selectAllSalesItems", params);
    }

    // 2. 전체 개수 조회 (페이징 total)
    @Override
    public int selectSalesItemCount(Map<String, Object> params) {
        return session.selectOne("SalesItemMapper.selectSalesItemCount", params);
    }

    // 3. 단일 상품 판매 내역 조회
    @Override
    public SalesItem selectSalesItemById(Long itemSalesId) {
        return session.selectOne("SalesItemMapper.selectSalesItemById", itemSalesId);
    }

    // 4. 상품 판매 내역 등록
    @Override
    public int insertSalesItem(SalesItem salesItem) {
        return session.insert("SalesItemMapper.insertSalesItem", salesItem);
    }

    // 5. 상품 판매 내역 수정
    @Override
    public int updateSalesItem(SalesItem salesItem) {
        return session.update("SalesItemMapper.updateSalesItem", salesItem);
    }

    // 6. 상품 판매 내역 삭제 (status = 'DELETED')
    @Override
    public int deleteSalesItem(Long itemSalesId) {
        return session.update("SalesItemMapper.deleteSalesItem", itemSalesId);
    }

    // 7. 상품 매출 통계 조회
    @Override
    public List<Map<String, Object>> selectItemSalesAnalytics(Map<String, Object> params) {
        return session.selectList("SalesItemMapper.selectItemSalesAnalytics", params);
    }

    // 8. 상품 매출 그래프 데이터 조회
    @Override
    public List<Map<String, Object>> selectItemSalesGraphData(Map<String, Object> params) {
        return session.selectList("SalesItemMapper.selectItemSalesGraphData", params);
    }
}
