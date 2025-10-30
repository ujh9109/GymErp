package com.example.gymerp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gymerp.dto.CurrentStockDto;
import com.example.gymerp.dto.PurchaseDto;
import com.example.gymerp.dto.StockAdjustRequestDto;
import com.example.gymerp.dto.StockAdjustmentDto;
import com.example.gymerp.service.StockService;

import lombok.RequiredArgsConstructor;


@RequestMapping("/v1")
@RestController
@RequiredArgsConstructor
public class StockController {
	
	private final StockService stockService;
	
	// 상품 재고 조회. 상품테이블에서 구분 코드와 상품명 가져오기, 입출고 테이블에서 입고수량-출고수량 표시
	@GetMapping("/stock")
    public List<CurrentStockDto> getProductStockList() {
        // 테스트 가정: 기본 offset=0, limit=50, type=null
        return stockService.getProductStockList();
	}
	// 상품 입고 내역 조회
	@GetMapping("/stock/{productId}/inbound")
	public List<PurchaseDto> getProductInboundDetail(@PathVariable int productId) {
	    return stockService.getProductInboundDetail(productId);
	}
	
	// 상품 출고 내역 조회
	@GetMapping("/stock/{productId}/outbound")
	public List<StockAdjustmentDto> getProductOutboundDetail(@PathVariable int productId) {
	    return stockService.getProductOutboundDetail(productId);
	}
	
	// 상품의 재고를 추가 및 차감 
	@PostMapping("/stock/{productId}/adjust")
	public ResponseEntity<Void> adjustProduct(
	        @PathVariable int productId,
	        @RequestBody StockAdjustRequestDto request
	) {
	    try {
	        stockService.adjustProduct(productId, request);
	        return ResponseEntity.ok().build();
	    } catch (IllegalArgumentException e) {
	    	System.out.println("⚠️ caught exception: " + e.getMessage());
	        return ResponseEntity.badRequest().build();
	    }
	}
}
