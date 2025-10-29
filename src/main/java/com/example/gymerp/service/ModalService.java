package com.example.gymerp.service;

import java.util.Map;

public interface ModalService {
	
	/* ================================
	   서비스 상품 선택 모달
	================================ */
	
	// 서비스 목록 조회
	Map<String, Object> getServiceList(String keyword, int page, int size);
	
	/* ================================
	   서비스 상품 선택 모달 끝
	================================ */
}
