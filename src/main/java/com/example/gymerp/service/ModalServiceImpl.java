package com.example.gymerp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

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
}
