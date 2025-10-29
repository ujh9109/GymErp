package com.example.gymerp.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Alias("EmpSchedule")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmpScheduleDto {
	private int calNum; 				// 달력 고유번호 (PK)
	private int shNum; 					// 스케줄 고유번호 (FK)
	private int empNum; 				// 직원 고유번호 (FK)
	private String color; 				// 캘린더 표시 색상
	private String calType; 			// 캘린더 유형 (ADMIN, GENERAL)
	private String refType; 			// 개인일정 참조유형 (PT, VACATION, ETC)
	private int refId; 					// 개인일정 참조번호
	private LocalDate date;				// 근무날짜
	private LocalDateTime startTime; 	// 시작시간
	private LocalDateTime endTime; 		// 종료시간
	private String memo; 				// 메모
	private String refDetail; 			// JOIN으로 가져온 참조 상세 내용

}
