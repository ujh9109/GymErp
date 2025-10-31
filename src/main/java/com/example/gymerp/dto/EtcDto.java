package com.example.gymerp.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EtcDto {
	private int etcNum; 				 // PK
	private int empNum; 				 // 직원 고유번호 (FK)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime etcCreated; 	 // 생성일
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime startTime; 		 // 시작 날짜
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime endTime; 			 // 종료 날짜
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime etcUpdatedAt;  // 수정일
	private String etcMemo;				 // 메모
	private String etcType;				 // 유형 (상담, 회의, 대회 등)
}
