package com.example.gymerp.repository;

import java.util.List;
import java.util.Map;

import com.example.gymerp.dto.SalesService;

public interface ModalDao {
	/* ================================
	   서비스 상품 선택 모달
	================================ */
	
	// 서비스 목록 조회
	List<SalesService> selectServiceList(Map<String, Object> params);
	
	// 서비스 선택 총 개수 조회
	int countServiceList(Map<String, Object> params);
	
	/* ================================
	   서비스 상품 선택 모달 끝
	================================ */
}
