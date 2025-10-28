package com.example.gymerp.repository;

import java.util.List;

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
}
