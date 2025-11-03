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

	/*
	 * ================================ 서비스 상품 선택 모달
	 * =================================
	 */

<<<<<<< HEAD
	@Override
	public List<ServiceDto> getServiceModalList(ServiceDto dto) {

		// 🔹 검색어 정리 (공백 제거, 빈문자 -> null)
		if (dto.getKeyword() != null && dto.getKeyword().trim().isEmpty()) {
			dto.setKeyword(null);
		}

		// 🔹 기본 limit (한 번에 조회할 행 수)
		int limit = 20; // 기본값
		try {
			// Controller에서 limit을 Map으로 넘겼을 때 dto에 담아둘 수 없으므로,
			// 필요 시 ServiceImpl 내부에서 고정 or 계산
			// 예: 스크롤 시 Controller에서 page별로 10/20 조정 가능
			limit = (int) dto.getEndRowNum() - (int) dto.getStartRowNum() + 1;
		} catch (Exception e) {
			// dto에 값이 없을 경우 기본 20행 유지
		}

		// 🔹 페이징 계산 (Oracle ROWNUM 기준)
		// Controller에서 startRowNum, endRowNum을 세팅하지 않은 경우만 자동 계산
		if (dto.getStartRowNum() <= 0 || dto.getEndRowNum() <= 0) {
			int page = dto.getEmpNum() > 0 ? dto.getEmpNum() : 1;
			int startRow = (page - 1) * limit + 1;
			int endRow = page * limit;
			dto.setStartRowNum(startRow);
			dto.setEndRowNum(endRow);
		}

		return dao.getServiceModalList(dto);
	}
=======
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
>>>>>>> upstream/develop

	@Override
	public int getServiceModalCount(ServiceDto dto) {
		if (dto.getKeyword() != null && dto.getKeyword().trim().isEmpty()) {
			dto.setKeyword(null);
		}
		return dao.getServiceModalCount(dto);
	}

	/*
	 * ================================ 서비스 상품 선택 모달 끝
	 * =================================
	 */

	/*
	 * ================================ 실물 상품 선택 모달 (추가)
	 * ================================
	 */

<<<<<<< HEAD
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

	/*
	 * ================================ 실물 상품 선택 모달 끝
	 * ================================
	 */

	/*
	 * ================================ [직원 선택 모달] ================================
	 */

	@Override
	public List<EmpDto> getEmployeeModalList(String keyword, int page, int limit) {
		Map<String, Object> params = new HashMap<>();
		params.put("keyword", keyword);
		params.put("offset", (page - 1) * limit);
		params.put("limit", limit);
		return dao.getEmployeeModalList(params); // 'dao'는 ModalDao 객체입니다.
	}

	@Override
	public int getEmployeeModalCount(String keyword) {
		Map<String, Object> params = new HashMap<>();
		params.put("keyword", keyword);
		return dao.getEmployeeModalCount(params);
	}

	/*
	 * ================================ [직원 선택 모달] 끝
	 * ================================
	 */
=======
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
>>>>>>> upstream/develop
}
