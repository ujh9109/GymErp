package com.example.gymerp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gymerp.dto.EmpDto;
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
    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getProductModalList(String keyword, int page, int limit) {
        int p = Math.max(page, 1), l = Math.max(limit, 1);
        int startRow = (p - 1) * l + 1;
        int endRow   = p * l;
        String kw = (keyword == null) ? "" : keyword.trim();

        Map<String, Object> param = new HashMap<>();
        param.put("keyword", kw);
        param.put("startRow", startRow);
        param.put("endRow", endRow);

        return dao.getProductModalList(param);
    }

    @Override
    @Transactional(readOnly = true)
    public int getProductModalCount(String keyword) {
        String kw = (keyword == null) ? "" : keyword.trim();
        return dao.getProductModalCount(Map.of("keyword", kw));
    }

	
	
	/* ================================
	   실물 상품 선택 모달 끝
	================================ */
	
	
	/* ================================
	   [직원 선택 모달]
	================================ */
	
	// ModalServiceImpl.java

    @Override
    @Transactional(readOnly = true)
    public List<EmpDto> getEmployeeModalList(String keyword, int page, int limit) {
        int p = Math.max(page, 1), l = Math.max(limit, 1);
        int startRow = (p - 1) * l + 1, endRow = p * l;
        String kw = (keyword == null) ? "" : keyword.trim(); // Map.of는 null 불가

        return dao.getEmployeeModalList(Map.of(
            "keyword", kw,
            "startRow", startRow,
            "endRow", endRow
        ));
    }

    @Override
    @Transactional(readOnly = true)
    public int getEmployeeModalCount(String keyword) {
        String kw = (keyword == null) ? "" : keyword.trim();
        return dao.getEmployeeModalCount(Map.of("keyword", kw));
    }

	
	/* ================================
	   [직원 선택 모달] 끝 
	================================ */	
}
