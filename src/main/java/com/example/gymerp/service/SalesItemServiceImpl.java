package com.example.gymerp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gymerp.dto.SalesItemDto;
import com.example.gymerp.repository.ProductDao;
import com.example.gymerp.repository.SalesItemDao;

import lombok.RequiredArgsConstructor;

import com.example.gymerp.dto.ProductDto;

@Service
@RequiredArgsConstructor
@Transactional
public class SalesItemServiceImpl implements SalesItemService {

    private final SalesItemDao salesItemDao;
    // private final StockService stockService; // 🚨 (삭제됨) 재고 서비스 주입 제거
    private final ProductDao productDao;

    @Override
    public Map<String, Object> getAllSalesItems(String startDate, String endDate, String productNameKeyword, Integer empNum, int page, int size) {
        
        // ... (생략: 기존의 페이징 및 조회 로직) ...
        
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        if (productNameKeyword != null && !productNameKeyword.trim().isEmpty()) {
            params.put("productNameKeyword", productNameKeyword); 
        }
        params.put("empNum", empNum);   
        int startRow = (page - 1) * size + 1;
        int endRow = page * size;
        params.put("startRow", startRow);
        params.put("endRow", endRow);
        
        List<SalesItemDto> salesItems = salesItemDao.selectAllSalesItems(params); 
        int totalCount = salesItemDao.selectSalesItemCount(params);             
        
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("list", salesItems);      
        resultMap.put("totalCount", totalCount); 
        resultMap.put("currentPage", page);
        resultMap.put("pageSize", size);

        return resultMap; 
    }

    @Override
    public SalesItemDto getSalesItemById(Long itemSalesId) {
        return salesItemDao.selectSalesItemById(itemSalesId);
    }

    @Override
    public int addSalesItem(SalesItemDto salesItem) {

        // 🚨 1. 재고 확인 로직 제거 (필요하다면 StockService가 아닌 ProductDao를 통해 조회하여 수행)
        // if (!stockService.isStockSufficient(salesItem.getProductId(), salesItem.getQuantity())) {
        //     throw new RuntimeException("판매 수량에 비해 상품 재고가 부족합니다.");
        // }

        // 2. 상품 정보 조회 및 DTO 필드 설정
        ProductDto product = productDao.getByNum(salesItem.getProductId());
        if (product == null) {
            throw new RuntimeException("상품 정보를 찾을 수 없습니다. productId: " + salesItem.getProductId());
        }
        salesItem.setProductName(product.getName());
        salesItem.setProductType(product.getCodeBId());
        salesItem.setStatus("ACTIVE"); 
        
		if (salesItem.getUnitPrice() == null) {
			salesItem.setUnitPrice(product.getPrice());
		}

        // 3. 판매 등록 전 설정 및 총액 계산
        salesItem.setCreatedAt(java.time.LocalDateTime.now());
        BigDecimal quantityBd = BigDecimal.valueOf(salesItem.getQuantity());
        salesItem.setTotalAmount(salesItem.getUnitPrice().multiply(quantityBd).setScale(0, RoundingMode.DOWN));

        int result = salesItemDao.insertSalesItem(salesItem); // 👈 핵심: 판매 등록만 수행

        // 🚨 4. 재고 차감 로직 완전히 제거 (adjustProduct 호출 제거)
        // stockService.adjustProduct(salesItem.getProductId(), adjustRequest);

        return result;
    }

 // 🌟 상품 판매 내역 수정 (재고 조정 로직 제거)
    @Override
    public int updateSalesItem(SalesItemDto salesItem) {
        Long itemSalesId = salesItem.getItemSalesId();
        
        // 1. 수정 전 기존 판매 내역 조회 (재고 계산이 아닌, 순수 데이터 수정이므로 로직 간소화)
        // 🚨 기존 재고 조정 로직 (oldSalesData 조회, quantityDiff 계산 등) 모두 제거
        
        // 2. 최종적으로 sales_item 테이블을 새로운 정보로 업데이트
        salesItem.setUpdatedAt(java.time.LocalDateTime.now());
        
        // 🚨 총액 계산
        BigDecimal quantityBd = BigDecimal.valueOf(salesItem.getQuantity());
        salesItem.setTotalAmount(salesItem.getUnitPrice().multiply(quantityBd).setScale(0, RoundingMode.DOWN)); 
        
        return salesItemDao.updateSalesItem(salesItem);
    }
    
 // SalesItemServiceImpl.java

    // 🌟 상품 판매 내역 삭제 (소프트 삭제 구현)
    @Override
    public int deleteSalesItem(Long itemSalesId) {
        
        // DAO의 deleteSalesItem 메서드를 호출합니다.
        // 이 메서드는 Mapper에서 status를 'DELETED'로 변경하는 UPDATE 쿼리를 실행합니다.
        return salesItemDao.deleteSalesItem(itemSalesId);
    }

	 // ... (getItemSalesAnalytics, getItemSalesGraphData 메서드 생략 - 변경 없음) ...
    // 상품 매출 통계 조회
	@Override
	public List<Map<String, Object>> getItemSalesAnalytics(String startDate, String endDate, List<Integer> itemIds,
			Integer memNum, Integer empNum) {
		
		Map<String, Object> params = new HashMap<>(); 
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("itemIds", itemIds); 
		params.put("memNum", memNum);	
		params.put("empNum", empNum);	
		
		return salesItemDao.selectItemSalesAnalytics(params);
	}
		
	 // 상품 매출 그래프 데이터 조회
	@Override
	public Map<String, List<Map<String, Object>>> getItemSalesGraphData(String startDate, String endDate,
			String groupByUnit) {
		
		Map<String, Object> params = new HashMap<>();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("groupByUnit", groupByUnit);
		
		List<Map<String, Object>> rawData = salesItemDao.selectItemSalesGraphData(params);
		
		Map<String, List<Map<String, Object>>> resultMap = new HashMap<>();
		resultMap.put("salesData", rawData);
		
		return resultMap;
	}
}