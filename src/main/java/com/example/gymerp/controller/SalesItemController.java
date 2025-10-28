package com.example.gymerp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.gymerp.dto.SalesItem;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class SalesItemController {

    private final com.example.gymerp.service.SalesItemService salesItemService;

    // 상품 판매 내역 전체 조회
    @GetMapping("/sales/products")
    public List<SalesItem> getAllSalesItems() {
        return salesItemService.getAllSalesItems();
    }

    // 상품 판매 내역 단일 조회
    @GetMapping("/sales/products/{id}")
    public SalesItem getSalesItemById(@PathVariable("id") Long id) {
        return salesItemService.getSalesItemById(id);
    }

    // 상품 판매 내역 등록
    @PostMapping("/sales/products")
    public int addSalesItem(@RequestBody SalesItem salesItem) {
        return salesItemService.addSalesItem(salesItem);
    }
    
    // 상품 판매 내역 수정
    @PutMapping("/sales/products/{id}")
    public int updateSalesItem(@PathVariable("id") Long id,
                               @RequestBody SalesItem salesItem) {
        salesItem.setItemSalesId(id);
        return salesItemService.updateSalesItem(salesItem);
    }

    // 상품 판매 내역 삭제 (status = 'DELETED')
    @PutMapping("/sales/products/{id}/delete")
    public int deleteSalesItem(@PathVariable("id") Long id) {
        return salesItemService.deleteSalesItem(id);
    }
}
