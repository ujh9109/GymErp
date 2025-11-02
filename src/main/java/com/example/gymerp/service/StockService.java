package com.example.gymerp.service;

import java.util.List;

import com.example.gymerp.dto.CurrentStockDto;
import com.example.gymerp.dto.PurchaseDto;
import com.example.gymerp.dto.StockAdjustRequestDto;
import com.example.gymerp.dto.StockAdjustmentDto;

public interface StockService {
	int getStockOne(int productId);
	Boolean isStockSufficient(int productId, int quantity);
    List<PurchaseDto> getProductInboundDetail(int productId, int page, int size); // 2-1
    List<StockAdjustmentDto> getProductOutboundDetail(int productId, int page, int size); // 2-2
    List<CurrentStockDto> getProductStockList(int page, int size, String keyword); // 2-3
    void adjustProduct(int productId, StockAdjustRequestDto request); // 3-*
}
