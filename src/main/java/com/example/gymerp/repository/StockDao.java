package com.example.gymerp.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.gymerp.dto.CurrentStockDto;
import com.example.gymerp.dto.PurchaseDto;
import com.example.gymerp.dto.StockAdjustmentDto;

public interface StockDao {
	// 2-1 입고 내역 조회
    List<PurchaseDto> getPurchaseList(int productId);

    // 2-2 출고 + 판매 내역 조회
    List<StockAdjustmentDto> getAdjustStockAndSalesList(int productId);

    // 2-3 현재 재고 현황 조회
    List<CurrentStockDto> getCurrentStockList();

    // 3-1 입고 등록
    int insertPurchase(PurchaseDto dto);

    // 3-2 출고 등록
    int insertStockAdjustment(StockAdjustmentDto dto);
    
}
