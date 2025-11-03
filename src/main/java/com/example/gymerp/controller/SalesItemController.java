package com.example.gymerp.controller;

import java.util.Map;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.gymerp.dto.SalesItemDto;
import com.example.gymerp.service.SalesItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/sales") 
@RequiredArgsConstructor
public class SalesItemController {

    private final SalesItemService salesItemService;

    // 1. 상품. 판매 등록 (CREATE) - 명세: POST /sales/products
    @PostMapping("/products") 
    public ResponseEntity<String> addSalesItem(@RequestBody SalesItemDto salesItem) {
        
        try {
            int result = salesItemService.addSalesItem(salesItem);
            
            if (result > 0) {
                return ResponseEntity.status(HttpStatus.CREATED).body("판매 내역이 성공적으로 등록되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("판매 내역 등록에 실패했습니다.");
            }
        } catch (Exception e) {
            // 로깅 처리 (e.getMessage())
            e.printStackTrace(); // 🚨 디버깅을 위해 스택 트레이스 추가
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("판매 내역 등록 중 서버 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 2. 상품 판매 전체 목록 조회 (READ ALL) - 명세: GET /sales/products
    @GetMapping("/products") 
    public ResponseEntity<Map<String, Object>> getAllSalesItems(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String productNameKeyword, 
            @RequestParam(required = false) Integer empNum,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        // (수정 2) Service 메소드 호출 시 변경된 파라미터 (productNameKeyword) 사용
        Map<String, Object> result = salesItemService.getAllSalesItems(startDate, endDate, productNameKeyword, empNum, page, size);
        
        return ResponseEntity.ok(result);
    }

    // 3. 상품 판매 단일 조회 (READ ONE) - 명세: GET /sales/products/{saleId}
    @GetMapping("/products/{itemSalesId}") 
    public ResponseEntity<SalesItemDto> getSalesItemById(@PathVariable Long itemSalesId) {
    	SalesItemDto salesItem = salesItemService.getSalesItemById(itemSalesId);
        if (salesItem != null) {
            return ResponseEntity.ok(salesItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 4. 상품 판매 수정 (UPDATE) - 명세: PUT /sales/products/{saleId}
    @PutMapping("/products/{itemSalesId}") 
    public ResponseEntity<String> updateSalesItem(
            @PathVariable Long itemSalesId, 
            @RequestBody SalesItemDto salesItem) {
        
        // DTO에 PathVariable로 받은 ID를 설정해야 Service에서 사용 가능
        // (SalesItem DTO에 setItemSalesId(Long) 메서드가 있다고 가정)
        salesItem.setItemSalesId(itemSalesId); 
        
        try {
            int result = salesItemService.updateSalesItem(salesItem); // Service에서는 DTO의 ID를 사용해야 함
            if (result > 0) {
                return ResponseEntity.ok("판매 내역이 성공적으로 수정되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("수정할 판매 내역을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("판매 내역 수정 중 서버 오류가 발생했습니다.");
        }
    }

    // 5. 상품 판매 삭제 (DELETE - 소프트 삭제) - 명세: DELETE /sales/products/{saleId}
    @DeleteMapping("/products/{itemSalesId}") 
    public ResponseEntity<String> deleteSalesItem(@PathVariable Long itemSalesId) {
        try {
            int result = salesItemService.deleteSalesItem(itemSalesId);
            if (result > 0) {
                return ResponseEntity.ok("판매 내역이 성공적으로 삭제(취소)되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제할 판매 내역을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("판매 내역 삭제 중 서버 오류가 발생했습니다.");
        }
    }

}