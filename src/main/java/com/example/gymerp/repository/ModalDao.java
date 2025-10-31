package com.example.gymerp.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.gymerp.dto.ServiceDto;
import com.example.gymerp.dto.ProductDto;

@Mapper
public interface ModalDao {
	
	/* ================================
	   서비스 상품 선택 모달
	================================ */

	// 서비스 상품 목록 조회
    List<ServiceDto> getServiceModalList(Map<String, Object> param);

    // 서비스 상품 전체 개수 조회
    int getServiceModalCount(Map<String, Object> param);
	
	/* ================================
	   서비스 상품 선택 모달 끝
	================================ */
    
    
    
    /* ================================
    아이템(상품) 선택 모달
	================================= */

	List<ProductDto> getProductModalList(Map<String, Object> param);
	
	/**
	 * 아이템 상품 전체 개수 조회 - 검색 조건 포함
	 */
	int getProductModalCount(Map<String, Object> param);
	
    /* ================================
    아이템(상품) 선택 모달 끝
	================================= */
}
