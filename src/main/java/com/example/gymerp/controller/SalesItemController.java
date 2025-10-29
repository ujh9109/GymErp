package com.example.gymerp.controller;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity; // ResponseEntity 사용을 위해 추가
import org.springframework.http.HttpStatus; // HttpStatus 사용을 위해 추가

import com.example.gymerp.dto.SalesItem;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class SalesItemController {

    private final com.example.gymerp.service.SalesItemService salesItemService;
    

    // 1. 상품 매출 목록 조회 (GET /sales/products) - 페이징 및 필터링 적용
    // 명세: GET /sales/products
    @GetMapping("/sales/products")
    public Map<String, Object> getAllSalesItems( 
        // 필터링 파라미터 추가
        @RequestParam(required = false) String startDate,
        @RequestParam(required = false) String endDate,
        @RequestParam(required = false) List<Long> itemIds, // 품목 선택
        @RequestParam(required = false) Long empNum,        // 직원 선택
        
        // 페이징 파라미터 추가
        @RequestParam(defaultValue = "1") int page,       
        @RequestParam(defaultValue = "10") int size       
    ) {
        // Service 호출 시 모든 파라미터 전달
        return salesItemService.getAllSalesItems(startDate, endDate, itemIds, empNum, page, size);
    }

    // 2. 상품 매출 단일 조회 (GET /sales/products/{id})
    // 명세: GET /sales/products/{saleId}
    @GetMapping("/sales/products/{id}")
    public SalesItem getSalesItemById(@PathVariable("id") Long id) {
        return salesItemService.getSalesItemById(id);
    }

    // 3. 상품 매출 등록 (POST /sales/products)
    // 명세: POST /sales/products
    @PostMapping("/sales/products")
    public int addSalesItem(@RequestBody SalesItem salesItem) {
        return salesItemService.addSalesItem(salesItem);
    }
    
    // 4. 상품 매출 수정 (PUT /sales/products/{id})
    // 명세: PUT /sales/products/{saleId}
    @PutMapping("/sales/products/{id}")
    public int updateSalesItem(@PathVariable("id") Long id,
                               @RequestBody SalesItem salesItem) {
        salesItem.setItemSalesId(id);
        return salesItemService.updateSalesItem(salesItem);
    }

    // 5. 상품 매출 삭제 (DELETE /sales/products/{id})
    // ⭐ 명세에 맞게 DELETE로 수정 (논리적 삭제는 Service에서 처리)
    @DeleteMapping("/sales/products/{id}")
    public ResponseEntity<String> deleteSalesItem(@PathVariable("id") Long id) {
        int result = salesItemService.deleteSalesItem(id);
        if (result > 0) {
             return new ResponseEntity<>("상품 매출 삭제 성공", HttpStatus.NO_CONTENT);
        } else {
             return new ResponseEntity<>("상품 매출 삭제 실패", HttpStatus.BAD_REQUEST);
        }
    }
}