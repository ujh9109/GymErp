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

    // 전체 상품 판매 내역 조회
    @Override
    public List<SalesItem> selectAllSalesItems() {
        return session.selectList("SalesItemMapper.selectAllSalesItems");
    }

    // 단일 상품 판매 내역 조회
    @Override
    public SalesItem selectSalesItemById(Long itemSalesId) {
        return session.selectOne("SalesItemMapper.selectSalesItemById", itemSalesId);
    }

    // 상품 판매 내역 등록
    @Override
    public int insertSalesItem(SalesItem salesItem) {
        return session.insert("SalesItemMapper.insertSalesItem", salesItem);
    }

    // 상품 판매 내역 수정
    @Override
    public int updateSalesItem(SalesItem salesItem) {
        return session.update("SalesItemMapper.updateSalesItem", salesItem);
    }

    // 상품 판매 내역 삭제 (status = 'DELETED')
    @Override
    public int deleteSalesItem(Long itemSalesId) {
        return session.update("SalesItemMapper.deleteSalesItem", itemSalesId);
    }
    
	 // SalesItemDaoImpl.java 구현체 파일에 추가할 내용 (SqlSession 사용)
	 // DAO 인터페이스에 정의된 메서드 시그니처와 동일해야 합니다.

	 // 상품 매출 통계 조회
	 @Override
	 public List<Map<String, Object>> selectItemSalesAnalytics(Map<String, Object> params) {
	     // "SalesItemMapper.selectItemSalesAnalytics"는 Mapper XML에 정의할 SQL의 ID입니다.
	     return session.selectList("SalesItemMapper.selectItemSalesAnalytics", params);
	 }
	
	 // 상품 매출 그래프 데이터 조회
	 @Override
	 public List<Map<String, Object>> selectItemSalesGraphData(Map<String, Object> params) {
	     // "SalesItemMapper.selectItemSalesGraphData"는 Mapper XML에 정의할 SQL의 ID입니다.
	     return session.selectList("SalesItemMapper.selectItemSalesGraphData", params);
	 }
}
