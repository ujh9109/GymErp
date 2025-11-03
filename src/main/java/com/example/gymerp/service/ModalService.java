package com.example.gymerp.service;

import java.util.List;
import com.example.gymerp.dto.EmpDto;
import com.example.gymerp.dto.ProductDto;
import com.example.gymerp.dto.ServiceDto;

/**
 * 모달 관련 비즈니스 로직을 정의한 서비스 인터페이스
 * - 각 모달(서비스, 상품, 직원 등)의 조회 및 페이징 관련 기능 담당
 */
public interface ModalService {

    /* =========================================================
       [서비스 상품 선택 모달]
       - 테이블: SERVICE
       - DTO: ServiceDto
       - 조건:
           · ISACTIVE = 1 (활성 상품만)
           · keyword 입력 시 NAME LIKE 검색
           · categoryCodes 존재 시 CODEBID IN 조건 필터
       - 정렬:
           · CODEBID LIKE '%PT%' → 최상위
           · NAME 내림차순
       - 페이징:
           · ROWNUM BETWEEN startRowNum AND endRowNum
    ========================================================= */

    // 서비스 상품 목록 조회
    List<ServiceDto> getServiceModalList(ServiceDto dto);

    // 서비스 상품 전체 개수 조회
    int getServiceModalCount(ServiceDto dto);

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
           · OFFSET #{offset} ROWS FETCH NEXT #{limit} ROWS ONLY
    ========================================================= */

    // 실물 상품 목록 조회
    List<ProductDto> getProductModalList(String keyword, int page, int limit);

    // 실물 상품 전체 개수 조회
    int getProductModalCount(String keyword);

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
           · OFFSET #{offset} ROWS FETCH NEXT #{limit} ROWS ONLY
    ========================================================= */

    // 직원 목록 조회
    List<EmpDto> getEmployeeModalList(String keyword, int page, int limit);

    // 직원 전체 개수 조회
    int getEmployeeModalCount(String keyword);

    /* =========================================================
       [직원 선택 모달 끝]
    ========================================================= */
}
