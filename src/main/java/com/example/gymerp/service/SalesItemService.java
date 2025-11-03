package com.example.gymerp.service;

import java.util.List;
import java.util.Map;

import com.example.gymerp.dto.SalesItemDto;



public interface SalesItemService {

	public Map<String, Object> getAllSalesItems(String startDate, String endDate, String productNameKeyword, Integer empNum, int page, int size);

    SalesItemDto getSalesItemById(Long itemSalesId);

    int addSalesItem(SalesItemDto salesItem);
    
    int updateSalesItem(SalesItemDto salesItem);


    int deleteSalesItem(Long itemSalesId);

	List<Map<String, Object>> getItemSalesAnalytics(String startDate, String endDate, List<Integer> itemIds, Integer memNum,
			Integer empNum);



	Map<String, List<Map<String, Object>>> getItemSalesGraphData(String startDate, String endDate, String groupByUnit);

	
}