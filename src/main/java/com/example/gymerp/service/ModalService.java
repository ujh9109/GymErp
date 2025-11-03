package com.example.gymerp.service;

import java.util.List;

import com.example.gymerp.dto.EmpDto;
import com.example.gymerp.dto.ProductDto;
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
	
	
	/* ================================
	   2. 실물 상품 선택 모달
	================================ */
	
	// 실물 상품 목록 조회
	List<ProductDto> getProductModalList(String keyword, int page, int limit);
	
	// 실물 상품 전체 개수 조회
	int getProductModalCount(String keyword);

	/* ================================
	   실물 상품 선택 모달 끝
	================================ */


	/* ================================
	   3. 직원 선택 모달
	================================ */

    // 기본 메소드 (그대로)
    List<EmpDto> getEmployeeModalList(String keyword, int page, int limit);
    int getEmployeeModalCount(String keyword);

    // 옵션: 기본값(1페이지, 10개)
    default List<EmpDto> getEmployeeModalList(String keyword) {
        return getEmployeeModalList(keyword, 1, 10);
    }
	
	/* ================================
	   직원 선택 모달 끝
	================================ */
}
