package com.example.gymerp.repository;

import java.util.List;
import java.util.Map;

import com.example.gymerp.dto.ServiceDto;

// 모달 데이터 조회를 위한 DAO 인터페이스
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
}
