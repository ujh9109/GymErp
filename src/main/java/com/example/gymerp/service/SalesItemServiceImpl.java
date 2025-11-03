package com.example.gymerp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gymerp.dto.SalesItemDto;
import com.example.gymerp.repository.SalesItemDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional // 클래스 레벨 트랜잭션 (모든 메서드에 적용됨)
public class SalesItemServiceImpl implements SalesItemService {

    private final SalesItemDao salesItemDao;
    
 // 상품 판매 내역 전체 조회 (페이징/필터링 적용)
    @Override
    public Map<String, Object> getAllSalesItems(String startDate, String endDate, List<Integer> itemIds, Integer empNum, int page, int size) {
        
        // 1. 파라미터 맵 준비
        Map<String, Object> params = new HashMap<>();
        
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("itemIds", itemIds); // Integer 리스트로 변경 반영
        params.put("empNum", empNum);   // Integer로 변경 반영
        
        // 2. 페이징 범위 계산
        // Oracle/MyBatis 환경의 RowNum 기반 페이징 공식 (혹은 OFFSET/FETCH 기반)
        int startRow = (page - 1) * size + 1;
        int endRow = page * size;

        params.put("startRow", startRow);
        params.put("endRow", endRow);
        
        // 3. DAO 호출 및 데이터 조회
        List<SalesItemDto> salesItems = salesItemDao.selectAllSalesItems(params); 
        int totalCount = salesItemDao.selectSalesItemCount(params);             
        
        // 4. 결과 맵 생성 및 반환
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("list", salesItems);      
        resultMap.put("totalCount", totalCount); 
        resultMap.put("currentPage", page);
        resultMap.put("pageSize", size);

        return resultMap;
    }

    // 상품 판매 내역 단일 조회
    @Override
    public SalesItemDto getSalesItemById(Long itemSalesId) {
        return salesItemDao.selectSalesItemById(itemSalesId);
    }

    // 상품 판매 내역 등록
    @Override
    public int addSalesItem(SalesItemDto salesItem) {
        salesItem.setCreatedAt(java.time.LocalDateTime.now());
        return salesItemDao.insertSalesItem(salesItem); 
    }

    // 🌟 1. 상품 판매 내역 수정 (재고 조정 로직 - 상품 변경 케이스 포함)
    @Override
    public int updateSalesItem(SalesItemDto salesItem) {
        Long itemSalesId = salesItem.getItemSalesId();
        
        // 1. 수정 전 기존 판매 내역 조회
        Map<String, Object> oldSalesData = salesItemDao.selectSalesItemForAdjustment(itemSalesId);
        
        if (oldSalesData == null || !"ACTIVE".equals(oldSalesData.get("STATUS"))) {
            // throw new ResourceNotFoundException("판매 내역을 찾을 수 없습니다.");
            return 0;
        }

        // 2. 기존 정보와 새 정보 추출
        int oldProductId = ((Number) oldSalesData.get("PRODUCTID")).intValue();
        int oldQuantity = ((Number) oldSalesData.get("OLDQUANTITY")).intValue();
        Object oldCodeBId = oldSalesData.get("CODEBID"); // codeBId 추출
        
        int newProductId = salesItem.getProductId();
        int newQuantity = salesItem.getQuantity();
        
	     // 3. 재고 조정 로직
	     if (oldProductId != newProductId) { 
	         
	         // 1-1. 기존 상품은 전부 환불 처리 (전체 수량 입고)
	         // 기존 상품 A의 판매 기록은 소멸되므로, A의 수량 전체를 재고로 환원해야 함.
	         Map<String, Object> refundParams = new HashMap<>();
	         refundParams.put("productId", oldProductId);
	         refundParams.put("codeBId", oldCodeBId);
	         refundParams.put("quantity", oldQuantity); // 기존 수량 전체를 환불
	         salesItemDao.insertPurchaseForRefund(refundParams);
	
	         // 1-2. 새로운 상품 (B)의 출고는 아래 updateSalesItem에서 판매 수량이 업데이트 되면서 자연스럽게 반영됨.
	     }
        // Case 2: 판매 상품은 동일하고, 수량만 변경된 경우 (oldProductId == newProductId)
        else {
            int quantityToRefund = oldQuantity - newQuantity; // 감소분 (양수일 경우 환불 필요)

            // 2-1. 수량이 감소했다면 (환불 발생)
            if (quantityToRefund > 0) {
                // 기존 상품의 감소분만큼만 환불 (입고) 처리
                Map<String, Object> refundParams = new HashMap<>();
                refundParams.put("productId", oldProductId);
                refundParams.put("codeBId", oldCodeBId);
                refundParams.put("quantity", quantityToRefund); // 감소한 만큼만 환불
                salesItemDao.insertPurchaseForRefund(refundParams);
            }
            // 2-2. 수량이 증가했다면 (quantityToRefund < 0), 별도 기록 없이 sales_item 업데이트로만 처리.
        }
        
        // 4. 최종적으로 sales_item 테이블을 새로운 정보로 업데이트
        salesItem.setUpdatedAt(java.time.LocalDateTime.now());
        return salesItemDao.updateSalesItem(salesItem);
    }

    // 🌟 2. 상품 판매 내역 삭제 (소프트 삭제 및 재고 환원 로직)
    @Override
    public int deleteSalesItem(Long itemSalesId) {
        
        // 1. 기존 판매 내역 정보 및 재고 관련 정보 조회
        Map<String, Object> oldSalesData = salesItemDao.selectSalesItemForAdjustment(itemSalesId);
        
        if (oldSalesData == null || !"ACTIVE".equals(oldSalesData.get("STATUS"))) {
            return 0; 
        }

        // 2. 환원해야 할 수량 (기존 판매 수량 전체)
        int quantityToRefund = ((Number) oldSalesData.get("OLDQUANTITY")).intValue();
        
        // 3. 재고 환원 (입고 내역 기록)
        Map<String, Object> purchaseParams = new HashMap<>();
        purchaseParams.put("productId", oldSalesData.get("PRODUCTID"));
        purchaseParams.put("codeBId", oldSalesData.get("CODEBID"));
        purchaseParams.put("quantity", quantityToRefund); // 기존 수량 전체를 환원
        
        salesItemDao.insertPurchaseForRefund(purchaseParams);
        
        // 4. SalesItem 테이블 소프트 삭제 (status = 'DELETED')
        return salesItemDao.deleteSalesItem(itemSalesId);
    }

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
		
	 
}