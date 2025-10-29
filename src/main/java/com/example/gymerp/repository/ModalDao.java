package com.example.gymerp.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.gymerp.dto.ServiceDto;

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
}
