package com.example.gymerp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SalesItem {

    private Long itemSalesId;      		// 상품 판매 내역 ID (PK)
    private String productId;      		// 상품 코드 (FK -> CODEB.codeBId)
    private String employeeId;     		// 직원 ID (FK -> employee.empNum)
    private String productName;    		// 상품명
    private Integer quantity;      		// 수량
    private Double unitPrice;      		// 단가
    private Double totalAmount;    		// 총액
    private String productType;    		// 구분
    private String status;         		// 상태
    private LocalDateTime createdAt;    // 등록일
    private LocalDateTime updatedAt;    // 수정일
}