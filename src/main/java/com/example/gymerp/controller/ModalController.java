package com.example.gymerp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

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
}
