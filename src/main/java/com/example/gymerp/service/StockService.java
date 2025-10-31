package com.example.gymerp.service;

import java.util.List;

import com.example.gymerp.dto.CurrentStockDto;
import com.example.gymerp.dto.PurchaseDto;
import com.example.gymerp.dto.StockAdjustRequestDto;
import com.example.gymerp.dto.StockAdjustmentDto;

public interface StockService {
	int getStockOne(int productId);
	Boolean isStockSufficient(int productId, int quantity);
	List<CurrentStockDto> getProductStockList();
    List<PurchaseDto> getProductInboundDetail(int productId);
    List<StockAdjustmentDto> getProductOutboundDetail(int productId);
    void adjustProduct(int productId, StockAdjustRequestDto request);
}
