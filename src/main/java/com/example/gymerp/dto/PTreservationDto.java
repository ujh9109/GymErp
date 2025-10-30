package com.example.gymerp.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PTreservationDto {
	private int regNum; 					// 예약/등록 고유번호 (PK)
	private int empNum; 					// 직원 번호 (FK)
	private int memNum; 					// 회원 번호 (FK)
	private int shNum; 						// 스케줄 번호 (FK)
	private LocalDateTime regCreated; 		// 생성일(작성 시각)
	private LocalDate regTime; 				// PT등록 작성일(일자)
	private LocalDate lastTime; 			// PT등록 종료일(일자)
	private LocalDate regDate; 				// PT하는 날(예약일자)
	private LocalDateTime regUpdatedAt; 	// 수정일
	private String regNote; 				// 메모
}
