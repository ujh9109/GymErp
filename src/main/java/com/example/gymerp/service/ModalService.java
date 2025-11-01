package com.example.gymerp.service;

import java.util.List;

import com.example.gymerp.dto.EmpDto;
import com.example.gymerp.dto.ProductDto;
import com.example.gymerp.dto.ServiceDto;

// 모달 비즈니스 로직을 정의한 서비스 인터페이스
public interface ModalService {
	
	/* ================================
	   1. 서비스 상품 선택 모달
	================================ */

	// 서비스 상품 목록 조회
	List<ServiceDto> getServiceModalList(String keyword, int page, int limit);

	// 서비스 상품 전체 개수 조회
	int getServiceModalCount(String keyword);

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

	// 직원 목록 조회
	List<EmpDto> getEmployeeModalList(String keyword, int page, int limit);
	
	// 직원 전체 개수 조회
	int getEmployeeModalCount(String keyword);
	
	/* ================================
	   직원 선택 모달 끝
	================================ */
	
}