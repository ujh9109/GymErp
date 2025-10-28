package com.example.gymerp.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.example.gymerp.service.ModalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/modal")
@RequiredArgsConstructor
public class ModalController {

    private final ModalService modalService;

    /* ================================
       서비스 상품 선택 모달
    ================================= */

    // 서비스 상품 목록 조회
    @GetMapping("/services")
    public Map<String, Object> getServiceList(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return modalService.getServiceList(keyword, page, size);
    }

    /* ================================
       서비스 상품 선택 모달 끝
    ================================= */
}
