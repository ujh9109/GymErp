package com.example.gymerp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.gymerp.dto.CurrentStockDto;
import com.example.gymerp.dto.PurchaseDto;
import com.example.gymerp.dto.StockAdjustRequestDto;
import com.example.gymerp.dto.StockAdjustmentDto;
import com.example.gymerp.repository.StockDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {
	
	private final StockDao stockDao;

	@Override
	public List<CurrentStockDto> getProductStockList() {
		
		return null;
	}

	@Override
	public List<PurchaseDto> getProductInboundDetail(int productId) {
		
		return null;
	}

	@Override
	public List<StockAdjustmentDto> getProductOutboundDetail(int productId) {
		
		return null;
	}

	@Override
	public void adjustProduct(int productId, StockAdjustRequestDto request) {
		// 수량 검증. 입출고시, 수량이 0보다 작으면 예외.
		System.out.println("✅ adjustProduct called with quantity=" + request.getQuantity());
	    if (request.getQuantity() <= 0) {
	        throw new IllegalArgumentException("수량은 1 이상이어야 합니다.");
	    }

	    // 나중에 DAO 호출 로직 들어올 자리
	    if ("ADD".equalsIgnoreCase(request.getAction())) {
	        System.out.println("입고 처리");
	    } else if ("SUBTRACT".equalsIgnoreCase(request.getAction())) {
	        System.out.println("차감 처리");
	    } else {
	    	System.out.println("⚠️ invalid action → 예외 발생 예정");
	        throw new IllegalArgumentException("올바르지 않은 action 값입니다.");
	    }
		
	}

	

}
