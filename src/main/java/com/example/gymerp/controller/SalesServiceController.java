package com.example.gymerp.controller;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;
import com.example.gymerp.dto.SalesService;
import com.example.gymerp.service.SalesServiceService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class SalesServiceController {

    private final SalesServiceService salesServiceService;

    /* ===============================
       [1. 조회]
    =============================== */

    // 전체 판매 내역 조회
    // (필터링, 페이징 기능은 별도 API로 확장 가능)
    @GetMapping("/sales/services")
    public List<SalesService> listSalesServices() {
        return salesServiceService.getAllSalesServices();
    }

    // ✅ 단일 판매 내역 조회
    @GetMapping("/sales/services/{id}")
    public SalesService getSalesService(@PathVariable Long id) {
        return salesServiceService.getSalesServiceById(id);
    }


    /* ===============================
       [2. 등록]
    =============================== */

    // 판매 등록 (회원권 or PT)
    // 내부적으로: 회원권/PT 등록 → 관련 로그 자동 생성
    @PostMapping("/sales/services")
    public Map<String, Object> createSalesService(@RequestBody SalesService salesService) {
        int result = salesServiceService.createSalesService(salesService);
        return Map.of(
            "result", result,
            "message", "판매 내역이 등록되었습니다."
        );
    }


    /* ===============================
       [3. 수정]
    =============================== */

    // 판매 수정 (부분환불 등 처리 포함)
    // 내부적으로: 
    //  - PT일 경우 → 부분환불 로그 생성
    //  - 회원권일 경우 → 기간 단축 처리
    @PutMapping("/sales/services/{id}")
    public Map<String, Object> updateSalesService(@PathVariable Long id,
                                                  @RequestBody SalesService salesService) {
        salesService.setServiceSalesId(id);
        int result = salesServiceService.updateSalesService(salesService);
        return Map.of(
            "result", result,
            "message", "판매 내역이 수정되었습니다."
        );
    }


    /* ===============================
       [4. 삭제]
    =============================== */

    // 판매 삭제 (논리삭제 + 환불 로그 포함)
    // 내부적으로:
    //  - PT일 경우 → 전체환불 로그 생성
    //  - 회원권일 경우 → rollback 처리
    @DeleteMapping("/sales/services/{id}")
    public Map<String, Object> deleteSalesService(@PathVariable Long id) {
        int result = salesServiceService.deleteSalesService(id);
        return Map.of(
            "result", result,
            "message", "판매 내역이 삭제되었습니다."
        );
    }

}
