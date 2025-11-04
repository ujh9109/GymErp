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

@Service
@RequiredArgsConstructor
public class ModalServiceImpl implements ModalService {

    private final ModalDao dao;

    /* =========================================================
       [서비스 상품 선택 모달]
       - 테이블: SERVICE
       - DTO: ServiceDto
       - 조건:
           · ISACTIVE = 1 (활성 상품만)
           · keyword 입력 시 NAME LIKE 검색
           · categoryCodes 존재 시 CODEBID IN 조건 필터
       - 페이징:
           · startRowNum, endRowNum 기준
       - 반환: 활성화된 서비스 상품 목록
    ========================================================= */
    @Override
    public List<ServiceDto> getServiceModalList(ServiceDto dto) {

        // ✅ keyword 공백 처리
        if (dto.getKeyword() != null && dto.getKeyword().trim().isEmpty()) {
            dto.setKeyword(null);
        }

        // ✅ 기본 limit 계산 (startRowNum, endRowNum 없을 경우)
        int limit = 20;
        if (dto.getStartRowNum() <= 0 || dto.getEndRowNum() <= 0) {
            int page = 1; // 기본 페이지 1
            int startRow = (page - 1) * limit + 1;
            int endRow = page * limit;
            dto.setStartRowNum(startRow);
            dto.setEndRowNum(endRow);
        }

        return dao.getServiceModalList(dto);
    }

    @Override
    public int getServiceModalCount(ServiceDto dto) {
        if (dto.getKeyword() != null && dto.getKeyword().trim().isEmpty()) {
            dto.setKeyword(null);
        }
        return dao.getServiceModalCount(dto);
    }

    /* =========================================================
       [서비스 상품 선택 모달 끝]
    ========================================================= */


    /* =========================================================
       [실물 상품 선택 모달]
       - 테이블: PRODUCT
       - 조건:
           · ISACTIVE = 1 (활성 상품만)
           · keyword 입력 시 NAME LIKE 검색
       - 페이징:
           · OFFSET / FETCH NEXT 방식
    ========================================================= */
    @Override
    public List<ProductDto> getProductModalList(String keyword, int page, int limit) {

        int offset = (page - 1) * limit;

        Map<String, Object> param = new HashMap<>();
        param.put("keyword", (keyword != null && !keyword.trim().isEmpty()) ? keyword : null);
        param.put("limit", limit);
        param.put("offset", offset);

        return dao.getProductModalList(param);
    }

    @Override
    public int getProductModalCount(String keyword) {
        Map<String, Object> param = new HashMap<>();
        param.put("keyword", (keyword != null && !keyword.trim().isEmpty()) ? keyword : null);
        return dao.getProductModalCount(param);
    }

    /* =========================================================
       [실물 상품 선택 모달 끝]
    ========================================================= */


    /* =========================================================
       [직원 선택 모달]
       - 테이블: EMPLOYEE
       - 조건:
           · EMP_STATUS = 'ACTIVE'
           · keyword 입력 시 이름 또는 이메일 LIKE 검색
       - 페이징:
           · OFFSET / FETCH NEXT 방식
    ========================================================= */
    @Override
    public List<EmpDto> getEmployeeModalList(String keyword, int page, int limit) {
        Map<String, Object> params = new HashMap<>();
        params.put("keyword", (keyword != null && !keyword.trim().isEmpty()) ? keyword : null);
        params.put("offset", (page - 1) * limit);
        params.put("limit", limit);
        return dao.getEmployeeModalList(params);
    }

    @Override
    public int getEmployeeModalCount(String keyword) {
        Map<String, Object> params = new HashMap<>();
        params.put("keyword", (keyword != null && !keyword.trim().isEmpty()) ? keyword : null);
        return dao.getEmployeeModalCount(params);
    }

    /* =========================================================
       [직원 선택 모달 끝]
    ========================================================= */
}
