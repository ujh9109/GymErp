package com.example.gymerp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gymerp.dto.SalesItem;
import com.example.gymerp.repository.SalesItemDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SalesItemServiceImpl implements SalesItemService {

    private final SalesItemDao salesItemDao;

    // 상품 판매 내역 전체 조회
    @Override
    public List<SalesItem> getAllSalesItems() {
        return salesItemDao.selectAllSalesItems();
    }

    // 상품 판매 내역 단일 조회
    @Override
    public SalesItem getSalesItemById(Long itemSalesId) {
        return salesItemDao.selectSalesItemById(itemSalesId);
    }

    // 상품 판매 내역 등록
    @Override
    public int addSalesItem(SalesItem salesItem) {
        return salesItemDao.insertSalesItem(salesItem);
    }

    // 상품 판매 내역 수정
    @Override
    public int updateSalesItem(SalesItem salesItem) {
        return salesItemDao.updateSalesItem(salesItem);
    }

    // 상품 판매 내역 삭제 (status = 'DELETED')
    @Override
    public int deleteSalesItem(Long itemSalesId) {
        return salesItemDao.deleteSalesItem(itemSalesId);
    }

	 // SalesItemServiceImpl.java 파일에서 수정할 내용 (기존 메서드 대체)
	    @Override
	    public List<Map<String, Object>> getItemSalesAnalytics(String startDate, String endDate, List<Long> itemIds,
	            Long memNum, Long empNum) {
	        
	        // 1. DAO로 전달할 파라미터 Map을 생성합니다.
	        // 기존: Map<String, Object> params = new HashMap()<>(); (오류 발생)
	        // 수정: Map<String, Object> params = new HashMap<>(); (정상 동작)
	        Map<String, Object> params = new HashMap<>(); // new HashMap() 뒤의 불필요한 괄호 제거
	        
	        params.put("startDate", startDate);
	        params.put("endDate", endDate);
	        params.put("itemIds", itemIds); // null 또는 빈 리스트가 올 수 있음
	        params.put("memNum", memNum);   // null이 올 수 있음
	        params.put("empNum", empNum);   // null이 올 수 있음
	        
	        // 2. DAO를 호출하고 결과를 반환합니다.
	        return salesItemDao.selectItemSalesAnalytics(params);
	    }
		
	    @Override
	    public Map<String, List<Map<String, Object>>> getItemSalesGraphData(String startDate, String endDate,
	            String groupByUnit) {
	        
	        // 1. DAO로 전달할 파라미터 Map을 생성합니다.
	        Map<String, Object> params = new HashMap<>();
	        params.put("startDate", startDate);
	        params.put("endDate", endDate);
	        params.put("groupByUnit", groupByUnit);
	        
	        // 2. DAO를 호출합니다. (반환 타입이 List<Map>임을 유의)
	        List<Map<String, Object>> rawData = salesItemDao.selectItemSalesGraphData(params);
	        
	        // 3. rawData를 Map<String, List<Map<String, Object>>> 형태로 가공하는 로직이 필요합니다.
	        // 그래프 데이터는 보통 (Date: [데이터], Item1: [데이터], Item2: [데이터]) 형태로 가공되어 반환됩니다.
	        // 현재는 간단히 DAO 결과를 그대로 Map에 담아 반환하거나, 그래프 타입별로 가공 로직을 작성해야 합니다.
	        // 임시로 List를 Map에 담아 반환하는 예시입니다. (실제 요구사항에 따라 로직이 달라집니다.)
	        Map<String, List<Map<String, Object>>> resultMap = new HashMap<>();
	        resultMap.put("salesData", rawData);
	        
	        return resultMap;
	    }
}
