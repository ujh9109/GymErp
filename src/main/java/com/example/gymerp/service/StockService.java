package com.example.gymerp.service;

import java.util.List;

import com.example.gymerp.dto.CurrentStockDto;
import com.example.gymerp.dto.PurchaseDto;
import com.example.gymerp.dto.StockAdjustRequestDto;
import com.example.gymerp.dto.StockAdjustmentDto;

public interface StockService {
    List<PurchaseDto> getProductInboundDetail(int productId); // 2-1
    List<StockAdjustmentDto> getProductOutboundDetail(int productId); // 2-2
    List<CurrentStockDto> getProductStockList(); // 2-3
    void adjustProduct(int productId, StockAdjustRequestDto request); // 3-*
}
