package com.example.gymerp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gymerp.dto.SalesItem;
import com.example.gymerp.repository.SalesItemDao; // SalesItemDao의 import는 유지합니다.

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SalesItemServiceImpl implements SalesItemService {

    private final SalesItemDao salesItemDao;

    // 상품 판매 내역 전체 조회 (페이징/필터링 적용)
    @Override
    public Map<String, Object> getAllSalesItems(String startDate, String endDate, List<Long> itemIds, Long empNum, int page, int size) {
        
        // 1. DAO로 전달할 파라미터 Map을 생성 및 조건 추가
        Map<String, Object> params = new HashMap<>();
        
        // 필터링 조건 (DAO/Mapper로 전달됨)
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("itemIds", itemIds); 
        params.put("empNum", empNum);
        
        // 2. Oracle DB 페이징을 위한 시작/종료 ROWNUM 계산
        int startRow = (page - 1) * size + 1;
        int endRow = page * size;

        params.put("startRow", startRow);
        params.put("endRow", endRow);
        
        // 3. DAO 호출: 목록과 전체 개수 두 번 호출
        List<SalesItem> salesItems = salesItemDao.selectAllSalesItems(params); // 페이지 목록 조회
        int totalCount = salesItemDao.selectSalesItemCount(params);             // 필터링된 전체 개수 조회
        
        // 4. 결과를 Map에 담아 반환
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("list", salesItems);      
        resultMap.put("totalCount", totalCount); 
        resultMap.put("currentPage", page);
        resultMap.put("pageSize", size);

        return resultMap;
    }

    // 상품 판매 내역 단일 조회 (기존 유지)
    @Override
    public SalesItem getSalesItemById(Long itemSalesId) {
        return salesItemDao.selectSalesItemById(itemSalesId);
    }

    // 상품 판매 내역 등록 (기존 유지)
    @Override
    public int addSalesItem(SalesItem salesItem) {
        salesItem.setCreatedAt(java.time.LocalDateTime.now());
        return salesItemDao.insertSalesItem(salesItem);
    }

    // 상품 판매 내역 수정 (기존 유지)
    @Override
    public int updateSalesItem(SalesItem salesItem) {
        salesItem.setUpdatedAt(java.time.LocalDateTime.now());
        return salesItemDao.updateSalesItem(salesItem);
    }

    // 상품 판매 내역 삭제 (status = 'DELETED') (기존 유지)
    @Override
    public int deleteSalesItem(Long itemSalesId) {
        SalesItem salesItem = salesItemDao.selectSalesItemById(itemSalesId);
        if (salesItem == null) {
            return 0; // or throw an exception
        }
        salesItem.setStatus("DELETED");
        salesItem.setUpdatedAt(java.time.LocalDateTime.now());
        return salesItemDao.updateSalesItem(salesItem);
    }

	 // 상품 매출 통계 조회 (기존 유지)
	@Override
	public List<Map<String, Object>> getItemSalesAnalytics(String startDate, String endDate, List<Long> itemIds,
			Long memNum, Long empNum) {
		
		Map<String, Object> params = new HashMap<>(); 
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("itemIds", itemIds); 
		params.put("memNum", memNum);	
		params.put("empNum", empNum);	
		
		return salesItemDao.selectItemSalesAnalytics(params);
	}
		
	 // 상품 매출 그래프 데이터 조회 (기존 유지)
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