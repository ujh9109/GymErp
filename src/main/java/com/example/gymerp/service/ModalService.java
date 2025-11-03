package com.example.gymerp.service;

import java.util.List;
import com.example.gymerp.dto.ServiceDto;

// 모달 비즈니스 로직을 정의한 서비스 인터페이스
public interface ModalService {
	
	/* ================================
	   서비스 상품 선택 모달
	================================ */

	// 서비스 상품 목록 조회
	List<ServiceDto> getServiceModalList(ServiceDto dto);

	// 서비스 상품 전체 개수 조회
	int getServiceModalCount(ServiceDto dto);

	/* ================================
	   서비스 상품 선택 모달 끝
	================================ */
}
