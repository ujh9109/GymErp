package com.example.gymerp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gymerp.dto.SalesService;
import com.example.gymerp.repository.ModalDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ModalServiceImpl implements ModalService {

    private final ModalDao modalDao;

    /* ================================
       서비스 상품 선택 모달
    ================================= */

    // 서비스 목록 조회 (검색 + 스크롤 + 페이지 혼합형)
    @Override
    public Map<String, Object> getServiceList(String keyword, int page, int size) {
        Map<String, Object> params = buildParams(keyword, page, size);

        // 1~2페이지(=10~20개)는 스크롤 로딩, 그 이후는 페이지 전환
        if (page > 2) {
            params.put("limit", 20); // 3페이지 이후는 한 번에 20개씩 조회
        }

        List<SalesService> list = modalDao.selectServiceList(params);
        int total = modalDao.countServiceList(params);

        return buildResult(list, total, page, size);
    }

    // 공통 파라미터 구성
    private Map<String, Object> buildParams(String keyword, int page, int size) {
        Map<String, Object> params = new HashMap<>();
        params.put("keyword", keyword);
        params.put("offset", (page - 1) * size);
        params.put("limit", size);
        return params;
    }

    // 결과 구성 (현재 페이지와 총 페이지 수 포함)
    private Map<String, Object> buildResult(List<SalesService> list, int total, int page, int size) {
        Map<String, Object> result = new HashMap<>();
        result.put("data", list);                // 현재 페이지 데이터
        result.put("total", total);              // 전체 데이터 개수
        result.put("currentPage", page);         // 현재 페이지 번호
        result.put("pageSize", size);            // 한 번에 조회하는 개수
        result.put("hasNext", (page * size) < total); // 다음 데이터 존재 여부
        return result;
    }

    /* ================================
       서비스 상품 선택 모달 끝
    ================================= */
}
