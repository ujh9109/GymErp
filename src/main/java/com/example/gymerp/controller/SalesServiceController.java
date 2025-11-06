package com.example.gymerp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import com.example.gymerp.dto.SalesService;
import com.example.gymerp.service.SalesServiceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@ConditionalOnProperty(                    // ✅ 기능 플래그
    prefix = "feature",
    name = "service-sale",
    havingValue = "true",
    matchIfMissing = false
)
public class SalesServiceController {

    private final SalesServiceService salesServiceService;

    /* ===============================
       [1. 조회]
    =============================== */

    // 전체 판매 내역 조회
    @GetMapping("/sales/services")
    public List<SalesService> listSalesServices() {
        return salesServiceService.getAllSalesServices();
    }

    // 단일 판매 내역 조회
    @GetMapping("/sales/services/{id}")
    public SalesService getSalesService(@PathVariable Long id) {
        return salesServiceService.getSalesServiceById(id);
    }

    /* ===============================
       [2. 등록]
    =============================== */

    @PostMapping("/sales/services")
    public Map<String, Object> createSalesService(@RequestBody SalesService salesService) {
        int result = salesServiceService.createSalesService(salesService);
        return Map.of("result", result, "message", "판매 내역이 등록되었습니다.");
    }

    /* ===============================
       [3. 수정]
    =============================== */

    @PutMapping("/sales/services/{id}")
    public Map<String, Object> updateSalesService(@PathVariable Long id,
                                                  @RequestBody SalesService salesService) {
        salesService.setServiceSalesId(id);
        int result = salesServiceService.updateSalesService(salesService);
        return Map.of("result", result, "message", "판매 내역이 수정되었습니다.");
    }

    /* ===============================
       [4. 삭제]
    =============================== */

    @DeleteMapping("/sales/services/{id}")
    public Map<String, Object> deleteSalesService(@PathVariable Long id) {
        int result = salesServiceService.deleteSalesService(id);
        return Map.of("result", result, "message", "판매 내역이 삭제되었습니다.");
    }
}
