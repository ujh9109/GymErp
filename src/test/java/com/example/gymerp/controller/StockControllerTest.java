package com.example.gymerp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.gymerp.dto.CurrentStockDto;
import com.example.gymerp.dto.PurchaseDto;
import com.example.gymerp.dto.StockAdjustRequestDto;
import com.example.gymerp.dto.StockAdjustmentDto;
import com.example.gymerp.service.StockService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 3-1. 실패하는 테스트 먼저: GET /stock
 * - 응답은 PageResponse 같은 래퍼 없이 "배열(JSON Array)" 그대로
 * - 컨트롤러 기본값 가정: offset=0, limit=50, type=null
 */
@WebMvcTest(controllers = StockController.class)
@AutoConfigureMockMvc(addFilters = false) // Security 필터 무시
class StockControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockitoBean
    StockService stockService;

    /*****************************/
    
    @Test
    @DisplayName("GET /stock - 상품 전체 재고 목록 조회(배열 응답)")
    void getProductStockList() throws Exception {
        List<CurrentStockDto> items = List.of(
                CurrentStockDto.builder()
                        .productId(1)
                        .productName("단백질 보충제")
                        .totalInbound(10)
                        .totalOutbound(1)
                        .totalSales(2)
                        .currentStock(7)
                        .build(),
                CurrentStockDto.builder()
                        .productId(2)
                        .productName("요가매트")
                        .totalInbound(5)
                        .totalOutbound(0)
                        .totalSales(0)
                        .currentStock(5)
                        .build()
        );

        // 컨트롤러가 서비스에 위임할 때의 기본 파라미터 가정
        when(stockService.getProductStockList()).thenReturn(items);

        mockMvc.perform(get("/v1/stock").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                // 배열 형태 검증
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].productId", is(1)))
                .andExpect(jsonPath("$[0].productName", is("단백질 보충제")))
                .andExpect(jsonPath("$[0].totalInbound", is(10)))
                .andExpect(jsonPath("$[0].totalOutbound", is(1)))
                .andExpect(jsonPath("$[0].totalSales", is(2)))
                .andExpect(jsonPath("$[0].currentStock", is(7)))
                .andExpect(jsonPath("$[1].productId", is(2)))
                .andExpect(jsonPath("$[1].currentStock", is(5)));
    }
    
    /*****************************/
    
    @Test
    @DisplayName("GET /v1/stock/{productId}/inbound - 특정 상품의 입고 내역 조회")
    void getProductInboundDetail() throws Exception {
        int productId = 1;

        var inboundList = List.of(
        	    PurchaseDto.builder()
        	        .purchaseId(101)
        	        .productId(productId)
        	        .codeBId("SUPPLEMENT")
        	        .createdAt(LocalDateTime.of(2025, 10, 1, 9, 0))
        	        .quantity(3)
        	        .notes("첫 입고")
        	        .build(),
        	    PurchaseDto.builder()
        	        .purchaseId(102)
        	        .productId(productId)
        	        .codeBId("EQUIP")
        	        .createdAt(LocalDateTime.of(2025, 10, 10, 10, 30))
        	        .quantity(5)
        	        .notes("추가 입고")
        	        .build()
        	);

        when(stockService.getProductInboundDetail(productId)).thenReturn(inboundList);

        mockMvc.perform(get("/v1/stock/{productId}/inbound", productId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].purchaseId", is(101)))
                .andExpect(jsonPath("$[0].quantity", is(3)))
                .andExpect(jsonPath("$[0].notes", is("첫 입고")));
    }
    
    /*****************************/
    
    @Test
    @DisplayName("GET /v1/stock/{productId}/outbound - 특정 상품의 출고 내역 조회")
    void getProductOutboundDetail() throws Exception {
        int productId = 1;

        var outboundList = List.of(
            StockAdjustmentDto.builder()
                .adjustmentId(201)
                .productId(productId)
                .codeBId("SUPPLEMENT")
                .createdAt(LocalDateTime.of(2025, 10, 15, 14, 0))
                .quantity(2)
                .notes("파손 처리")
                .build(),
            StockAdjustmentDto.builder()
                .adjustmentId(202)
                .productId(productId)
                .codeBId("EQUIP")
                .createdAt(LocalDateTime.of(2025, 10, 20, 16, 30))
                .quantity(1)
                .notes("지점 이동")
                .build()
        );

        when(stockService.getProductOutboundDetail(productId)).thenReturn(outboundList);

        mockMvc.perform(get("/v1/stock/{productId}/outbound", productId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].adjustmentId", is(201)))
                .andExpect(jsonPath("$[0].quantity", is(2)))
                .andExpect(jsonPath("$[0].notes", is("파손 처리")));
    }
    
    /*****************************/
    
    @Test
    @DisplayName("POST /v1/stock/{productId}/adjust - 재고 추가/차감 요청")
    void adjustProduct() throws Exception {
        int productId = 1;

        var addRequest = StockAdjustRequestDto.builder()
                .action("ADD")
                .quantity(3)
                .notes("테스트 입고")
                .build();

        mockMvc.perform(post("/v1/stock/{productId}/adjust", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addRequest)))
                .andExpect(status().isOk());
    }

    /*****************************/
    
    @Test
    @DisplayName("POST /v1/stock/{productId}/adjust - 0 이하 수량이면 400 반환")
    void adjustProduct_invalidQty() throws Exception {
        int productId = 1;

        var badRequest = StockAdjustRequestDto.builder()
                .action("SUBTRACT")
                .quantity(0)
                .notes("테스트 오류")
                .build();
        
        doThrow(new IllegalArgumentException("수량은 1 이상이어야 합니다."))
        .when(stockService)
        .adjustProduct(anyInt(), any());

        mockMvc.perform(post("/v1/stock/{productId}/adjust", productId)
        			.characterEncoding("UTF-8") 
        			.contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(badRequest)))
                .andExpect(status().isBadRequest());
    }

	
}
