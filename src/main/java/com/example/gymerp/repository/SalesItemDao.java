package com.example.gymerp.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.gymerp.dto.SalesItem;

@Mapper
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
}
