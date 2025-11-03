package com.example.gymerp.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.gymerp.dto.EmpDto;
import com.example.gymerp.dto.ProductDto;
import com.example.gymerp.dto.ServiceDto;

@Mapper
public interface ModalDao {

    /* ================================
       서비스 상품 선택 모달
    ================================= */

    // 서비스 상품 목록 조회
    List<ServiceDto> getServiceModalList(ServiceDto dto);

    // 서비스 상품 전체 개수 조회
    int getServiceModalCount(ServiceDto dto);

    /* ================================
       서비스 상품 선택 모달 끝
    ================================= */
    
    /* ================================
    아이템(상품) 선택 모달
	================================= */

	List<ProductDto> getProductModalList(Map<String, Object> param);

	// 아이템(상품) 갯수 조회
	int getProductModalCount(Map<String, Object> param);
	
    /* ================================
    아이템(상품) 선택 모달 끝
	================================= */
	
	/* ================================
	   직원 선택 모달 
	================================ */

	// 직원 목록 조회 (검색 + 페이징)
	List<EmpDto> getEmployeeModalList(Map<String, Object> param);

	// 직원 전체 수 조회 (검색)
	int getEmployeeModalCount(Map<String, Object> param);

	/* ================================
			   직원 선택 모달 끝
	================================ */
	
		
		
	
	/* =============================
	 		회원 모달 시작
	 ===============================*/
	
	
	
	
	
	/* =============================
				회원 모달 끝
	===============================*/
	
	
    
}
