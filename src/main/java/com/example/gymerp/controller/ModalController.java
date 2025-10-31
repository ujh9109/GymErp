package com.example.gymerp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.example.gymerp.dto.EmpDto;
import com.example.gymerp.dto.ProductDto;
import com.example.gymerp.dto.ServiceDto;
import com.example.gymerp.service.ModalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ModalController {

    private final ModalService modalService;

    /* ================================
       [서비스 상품 선택 모달]
    ================================ */

    // 서비스 상품 목록 조회 (검색 + 페이징 포함)
    @GetMapping("/modals/services")
    public Map<String, Object> getServiceModalList(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {

        List<ServiceDto> list = modalService.getServiceModalList(keyword, page, limit);
        int totalCount = modalService.getServiceModalCount(keyword);

        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("totalCount", totalCount);
        result.put("currentPage", page);

        return result;
    }

    /* ================================
       [서비스 상품 선택 모달 끝]
    ================================ */
    
    /* ================================
    	[실물 상품 선택 모달]
 	================================ */

	 // 실물 상품 목록 조회 (검색 + 페이징 포함)
	 @GetMapping("/modals/products") // RESTful API 디자인에 따라 /modals/products로 엔드포인트 설정
	 public Map<String, Object> getProductModalList(
	         @RequestParam(required = false) String keyword,
	         @RequestParam(defaultValue = "1") int page,
	         @RequestParam(defaultValue = "20") int limit) {
	
	     // 1. Service를 통해 실물 상품 목록과 전체 개수 조회
	     List<ProductDto> list = modalService.getProductModalList(keyword, page, limit);
	     int totalCount = modalService.getProductModalCount(keyword);
	
	     // 2. 결과를 Map에 담아 반환
	     Map<String, Object> result = new HashMap<>();
	     result.put("list", list);
	     result.put("totalCount", totalCount);
	     result.put("currentPage", page);
	     
	     // 🌟 [예외 처리 보류] 검색 결과가 없는 경우 204 No Content를 반환할 수도 있으나,
	     // Service 상품과 동일하게 totalCount=0으로 반환하여 프론트엔드가 처리하도록 함.
	
	     return result;
	 }
	
	 /* ================================
	    [실물 상품 선택 모달 끝]
	 ================================ */    
	 
	 /* ================================
     [직원 선택 모달] 🌟 추가된 부분
  ================================ */

	  // 직원 목록 조회 (검색 + 페이징 포함)
	  @GetMapping("/modals/employees")
	  public Map<String, Object> getEmployeeModalList(
	          @RequestParam(value = "keyword", required = false) String keyword,
	          @RequestParam(value = "page", defaultValue = "1") int page,
	          @RequestParam(value = "limit", defaultValue = "20") int limit) {
	
	      // 1. Service를 통해 직원 목록과 전체 개수 조회
	      List<EmpDto> list = modalService.getEmployeeModalList(keyword, page, limit);
	      int totalCount = modalService.getEmployeeModalCount(keyword);
	
	      // 2. 결과를 Map에 담아 반환
	      Map<String, Object> result = new HashMap<>();
	      result.put("list", list);
	      result.put("totalCount", totalCount);
	      result.put("currentPage", page);
	
	      return result;
	  }
	
	  /* ================================
	     [직원 선택 모달 끝]
	  ================================ */	 
}
