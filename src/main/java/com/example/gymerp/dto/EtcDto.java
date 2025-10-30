package com.example.gymerp.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EtcDto {
	private int etcNum; 				 // PK
	private int empNum; 				 // 직원 고유번호 (FK)
	private LocalDateTime etcCreated; 	 // 생성일
	private LocalDate startTime; 		 // 시작 날짜
	private LocalDate endTime; 			 // 종료 날짜
	private LocalDateTime etcUpdatedAt;  // 수정일
	private String etcMemo;				 // 메모
	private String etcType;				 // 유형 (상담, 회의, 대회 등)
}
