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

    /* ================================
       서비스 상품 선택 모달
    ================================= */

    @Override
    public List<ServiceDto> getServiceModalList(ServiceDto dto) {

        // 🔹 검색어 정리 (공백 제거, 빈문자 -> null)
        if (dto.getKeyword() != null && dto.getKeyword().trim().isEmpty()) {
            dto.setKeyword(null);
        }

        // 🔹 기본 limit (한 번에 조회할 행 수)
        int limit = 20;  // 기본값
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
            int page = dto.getPrevNum() > 0 ? dto.getPrevNum() : 1;
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
