package com.example.gymerp.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

	private int productId;
	private String codeA;
	private String codeB;
	private String name;
	private int price;
	private Boolean isActive;
	private String note;
	private int quantity; // 입고 내역 테이블에 수량으로 저장할 필드
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	//페이징 처리를 위한 필드
	private int startRowNum;
	private int endRowNum;
	//프로필 이미지 출력을 위한 필드
	private String ProfileImage;
	//이전글, 다음글 처리를 위한 필드
	private int prevNum;
	private int nextNum;
	//검색 키워드를 담기 위한 필드
	private String keyword;
	//검색 조건을 담기 위한 필드
	private List<String> categoryCodes;
}
