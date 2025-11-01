package com.example.gymerp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gymerp.dto.CurrentStockDto;
import com.example.gymerp.dto.ProductDto;
import com.example.gymerp.dto.PurchaseDto;
import com.example.gymerp.dto.StockAdjustRequestDto;
import com.example.gymerp.dto.StockAdjustmentDto;
import com.example.gymerp.repository.ProductDao;
import com.example.gymerp.repository.StockDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {
	
	private final StockDao stockDao;
	private final ProductDao productDao;

<<<<<<< HEAD
	
=======
	// 1-1. 가용 재고 조회
	@Override
	public int getStockOne(int productId) {
		
		return stockDao.getAvailableQty(productId);
	}
	
	// 1-2. 판매 가능 여부 리턴
	@Override
	public Boolean isStockSufficient(int productId, int quantity) {
		Boolean result = false;
		if(stockDao.getAvailableQty(productId)>=quantity) {
			result = true;
		}else {
			result = false;
		}
			
		return result;
	}	
	
	// 2-1. 입고내역 리스트 (현재 재고 현황)
    @Override
    @Transactional(readOnly = true)
    public List<CurrentStockDto> getProductStockList() {
        return stockDao.getCurrentStockList();
    }
>>>>>>> 89d0e36f12b2493f79eaa7efd028851ab1a91744

    // 2-1. 상품 하나의 입고(인바운드) 내역 조회
    @Override
    @Transactional(readOnly = true)
    public List<PurchaseDto> getProductInboundDetail(int productId) {
        if (productId <= 0) {
            throw new IllegalArgumentException("유효하지 않은 productId 입니다.");
        }
        return stockDao.getPurchaseList(productId);
    }

    // 2-2. 상품 하나의 출고 + 판매 내역 조회
    @Override
    @Transactional(readOnly = true)
    public List<StockAdjustmentDto> getProductOutboundDetail(int productId) {
        if (productId <= 0) {
            throw new IllegalArgumentException("유효하지 않은 productId 입니다.");
        }
        return stockDao.getAdjustStockAndSalesList(productId);
    }
    
    // 2-3. 현재 재고내역 리스트 (현재 재고 현황)
    @Override
    @Transactional(readOnly = true)
    public List<CurrentStockDto> getProductStockList() {
        return stockDao.getCurrentStockList();
    }

	// 3. adjustProduct
	@Override
	@Transactional
	public void adjustProduct(int productId, StockAdjustRequestDto request) {
		// 수량 검증. 입출고시, 수량이 0보다 작으면 예외.
		System.out.println(" adjustProduct called with quantity=" + request.getQuantity());
	    if (request.getQuantity() <= 0) {
	        throw new IllegalArgumentException("수량은 1 이상이어야 합니다.");
	    }
	    
	   ProductDto productDto = productDao.getByNum(productId);
	    if (productDto == null) {
	        throw new IllegalArgumentException("유효하지 않은 상품입니다: productId=" + productId);
	    }

	    String codeBId = productDto.getCodeBId();

	    // 나중에 DAO 호출 로직 들어올 자리
	    if ("ADD".equalsIgnoreCase(request.getAction())) { // 대소문자 무시하고 문자열 비교 
	        PurchaseDto purchase = PurchaseDto.builder()
	        		.productId(productId)
	        		.codeBId(codeBId) 
	        		.quantity(request.getQuantity())
	        		.notes(request.getNotes())
	        		.build();
	        stockDao.insertPurchase(purchase); // call DaoImpl 3-1
	    } else if ("SUBTRACT".equalsIgnoreCase(request.getAction())) {
	        StockAdjustmentDto adjust = StockAdjustmentDto.builder()
	        		.productId(productId)
	        		.codeBId(codeBId) 
	        		.quantity(request.getQuantity())
	        		.notes(request.getNotes())
	        		.build();
	        stockDao.insertStockAdjustment(adjust); // call DaoImpl 3-2
	    } else {
	    	System.out.println("⚠️ invalid action → 예외 발생 예정");
	        throw new IllegalArgumentException("올바르지 않은 action 값입니다.");
	    }
		
	}


	

}
