package com.example.gymerp.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder 
@NoArgsConstructor 
@AllArgsConstructor
@Data
public class SalesService {
	private Long serviceSalesId;   			// 서비스 판매 내역 ID (PK)
    private String productId;      			// 상품 코드 (FK -> CODEB.codeBId)
    private String productName;    			// 상품명
    private String employeeId;     			// 직원 ID (FK -> employee.empNum)
    private String memberId;       			// 회원 ID (FK -> member.memNum)
    private Integer baseCount;     			// 기본 횟수/기간
    private Integer actualCount;   			// 실제 횟수/기간
    private Double discount;       			// 할인액
    private Double baseAmount;     			// 기존 총액
    private Double actualAmount;   			// 실제 총액
    private Double avgPrice;       			// 평균가
    private String productType;    			// 구분 (PT / 이용권 등)
    private String status;         			// 상태
    private LocalDateTime createdAt;        // 등록일
    private LocalDateTime updatedAt;        // 수정일
}
