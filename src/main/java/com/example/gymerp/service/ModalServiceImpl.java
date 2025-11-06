package com.example.gymerp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.gymerp.dto.EmpDto;
import com.example.gymerp.dto.ProductDto;
import com.example.gymerp.dto.ServiceDto;
import com.example.gymerp.repository.ModalDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ModalServiceImpl implements ModalService {
    private final ModalDao dao;

    // ---------- 공통 유틸 ----------
    private String norm(String s) {
        return (s != null && !s.trim().isEmpty()) ? s : null;
    }

    private int safePage(int page) {
        return (page < 1) ? 1 : page;
    }

    private int safeLimit(int limit) {
        return (limit < 1) ? 10 : limit;
    }

    private int toOffset(int page, int limit) {
        return (safePage(page) - 1) * safeLimit(limit);
    }

    /* =========================================================
       [서비스 상품 선택 모달] (SERVICE)
       - DTO: ServiceDto (startRowNum, endRowNum 사용)
       - keyword 공백 -> null
       - startRowNum/endRowNum가 없으면 1~20 기본값
    ========================================================= */
    @Override
    public List<ServiceDto> getServiceModalList(ServiceDto dto) {

        // keyword 공백 처리
        if (dto.getKeyword() != null && dto.getKeyword().trim().isEmpty()) {
            dto.setKeyword(null);
        }

        // limit 계산 (start/end가 들어온 경우 그대로 사용)
        int limit = 20;
        if (dto.getStartRowNum() > 0 && dto.getEndRowNum() > 0 && dto.getEndRowNum() >= dto.getStartRowNum()) {
            limit = (int) (dto.getEndRowNum() - dto.getStartRowNum() + 1);
        }

        // startRowNum / endRowNum 자동 세팅 (없을 때만)
        if (dto.getStartRowNum() <= 0 || dto.getEndRowNum() <= 0) {
            int startRow = 1;
            int endRow = limit; // 기본 1~20
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
       [실물 상품 선택 모달]  (PRODUCT)
       - 조건: ISACTIVE = 1, keyword LIKE (옵션)
       - 페이징: ROWNUM BETWEEN ? AND ? (Oracle ROWNUM 방식)
    ========================================================= */
    @Override
    public List<ProductDto> getProductModalList(String keyword, int page, int limit) {
        // 1. 안전한 limit 값 확보 (safeLimit 메서드는 이미 있다고 가정)
        int fixedLimit  = safeLimit(limit);
        
        // 2. startRow와 endRow 계산 (ROWNUM 방식)
        int startRow = (safePage(page) - 1) * fixedLimit + 1;
        int endRow   = safePage(page) * fixedLimit;

        Map<String, Object> param = new HashMap<>();
        param.put("keyword", norm(keyword));
        
        // 3. Mapper에서 기대하는 파라미터 이름으로 값 설정
        param.put("startRow", startRow); // ROWNUM 시작 값
        param.put("endRow",   endRow);   // ROWNUM 끝 값

        return dao.getProductModalList(param);
    }
    
    @Override
    public int getProductModalCount(String keyword) {
        // Map에 keyword를 담아 DAO로 전달하는 로직입니다.
        Map<String, Object> param = new HashMap<>();
        param.put("keyword", keyword); // keyword를 Map에 담습니다.
        
        // DAO 메소드 호출
        return dao.getProductModalCount(param); 
    }

    /* =========================================================
       [직원 선택 모달]  (EMPLOYEE)
       - 조건: EMP_STATUS = 'ACTIVE', keyword LIKE (이름/이메일)
       - 페이징: ROWNUM BETWEEN ? AND ? (Oracle ROWNUM 방식으로 통일)
    ========================================================= */
    @Override
    public List<EmpDto> getEmployeeModalList(String keyword, int page, int limit) {

        int fixedLimit  = safeLimit(limit);
        
        // Oracle ROWNUM 스타일 페이지네이션 계산
        int startRow = (safePage(page) - 1) * fixedLimit + 1;
        int endRow   = safePage(page) * fixedLimit;


        Map<String, Object> params = new HashMap<>();
        params.put("keyword", norm(keyword));
        params.put("startRow", startRow); // Mapper가 요구하는 ROWNUM 시작 값
        params.put("endRow",   endRow);   // Mapper가 요구하는 ROWNUM 끝 값
        
        // 기존 offset, limit 파라미터는 제거됨

        return dao.getEmployeeModalList(params);
    }

    @Override
    public int getEmployeeModalCount(String keyword) {
        Map<String, Object> params = new HashMap<>();
        params.put("keyword", norm(keyword));
        return dao.getEmployeeModalCount(params);
    }
}
