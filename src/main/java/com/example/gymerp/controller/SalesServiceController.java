package com.example.gymerp.controller;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;
import com.example.gymerp.dto.SalesService;
import com.example.gymerp.service.SalesServiceService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SalesServiceController {

    private final SalesServiceService salesServiceService;

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

    // 판매 등록
    @PostMapping("/sales/services")
    public Map<String, Object> createSalesService(@RequestBody SalesService salesService) {
        int result = salesServiceService.createSalesService(salesService);
        return Map.of("result", result, "message", "판매 내역이 등록되었습니다.");
    }

    // 판매 수정
    @PutMapping("/sales/services/{id}")
    public Map<String, Object> updateSalesService(@PathVariable Long id,
                                                  @RequestBody SalesService salesService) {
        salesService.setServiceSalesId(id);
        int result = salesServiceService.updateSalesService(salesService);
        return Map.of("result", result, "message", "판매 내역이 수정되었습니다.");
    }

    // 판매 삭제
    @DeleteMapping("/sales/services/{id}")
    public Map<String, Object> deleteSalesService(@PathVariable Long id) {
        int result = salesServiceService.deleteSalesService(id);
        return Map.of("result", result, "message", "판매 내역이 삭제되었습니다.");
    }

    // 페이징 조회
    @GetMapping("/sales/services/paged")
    public Map<String, Object> getPagedSales(@RequestParam(required = false) String keyword,
                                             @RequestParam(defaultValue = "1") int page,
                                             @RequestParam(defaultValue = "10") int scrollStep,
                                             @RequestParam(required = false) Long empNum,
                                             @RequestParam(required = false) Long memNum,
                                             @RequestParam(required = false) List<Long> serviceIds,
                                             @RequestParam(required = false) String startDate,
                                             @RequestParam(required = false) String endDate) {
        return salesServiceService.getPagedServiceSales(keyword, page, scrollStep,
                empNum, memNum, serviceIds, startDate, endDate);
    }

    // 그래프 조회
    @GetMapping("/sales/services/graph")
    public List<Map<String, Object>> getSalesGraph(@RequestParam(required = false) String startDate,
                                                   @RequestParam(required = false) String endDate,
                                                   @RequestParam(required = false) List<Long> serviceIds,
                                                   @RequestParam(required = false) Long memNum,
                                                   @RequestParam(required = false) Long empNum) {
        return salesServiceService.getServiceSalesGraph(startDate, endDate, serviceIds, memNum, empNum);
    }
}
