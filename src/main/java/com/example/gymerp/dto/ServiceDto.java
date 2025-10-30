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
public class ServiceDto {
	
	private int serviceId;
	private int empNum;
	private String codeA;
	private String codeB;
	private String name;
	private BigDecimal price;
	private Boolean isActive;
	private String note;
	private int serviceValue;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	//페이징 처리를 위한 필드
	private int startRowNum;
	private int endRowNum;
	//이전글, 다음글 처리를 위한 필드
	private int prevNum;
	private int nextNum;
	//검색 키워드를 담기 위한 필드
	private String keyword;
	//검색 조건을 담기 위한 필드
	private List<String> categoryCodes;
}
