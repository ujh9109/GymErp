package com.example.gymerp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.gymerp.dto.ProductDto;
import com.example.gymerp.dto.ServiceDto;
import com.example.gymerp.repository.ModalDao;

import lombok.RequiredArgsConstructor;

// 모달 비즈니스 로직 구현체
@Service
@RequiredArgsConstructor
public class ModalServiceImpl implements ModalService {

    private final ModalDao dao;

    /* ================================
       서비스 상품 선택 모달
    ================================= */

    // 서비스 상품 목록 조회
    @Override
    public List<ServiceDto> getServiceModalList(String keyword, int page, int limit) {
        int offset = (page - 1) * limit;
        Map<String, Object> param = new HashMap<>();
        param.put("keyword", keyword);
        param.put("limit", limit);
        param.put("offset", offset);
        return dao.getServiceModalList(param);
    }

    // 서비스 상품 전체 개수 조회
    @Override
    public int getServiceModalCount(String keyword) {
        Map<String, Object> param = new HashMap<>();
        param.put("keyword", keyword);
        return dao.getServiceModalCount(param);
    }

    /* ================================
       서비스 상품 선택 모달 끝
    ================================= */
    
    
    
    /* ================================
	   실물 상품 선택 모달 (추가)
	================================ */
	
	// 실물 상품 목록 조회
	@Override
	public List<ProductDto> getProductModalList(String keyword, int page, int limit) {
     
     // 1. 페이징 시작 지점 (offset) 계산
     int offset = (page - 1) * limit;

     // 2. DAO에 전달할 파라미터 Map 생성 및 값 할당
     Map<String, Object> param = new HashMap<>();
     param.put("keyword", keyword);
     param.put("limit", limit);
     param.put("offset", offset);

     // 3. DAO 호출
	    return dao.getProductModalList(param);
	}
	
	// 실물 상품 전체 개수 조회
	@Override
	public int getProductModalCount(String keyword) {
     // 1. DAO에 전달할 파라미터 Map 생성 및 값 할당 (검색 조건만 전달)
     Map<String, Object> param = new HashMap<>();
     param.put("keyword", keyword);
     
     // 2. DAO 호출
	    return dao.getProductModalCount(param);
	}
	
	
	/* ================================
	   실물 상품 선택 모달 끝
	================================ */
}
