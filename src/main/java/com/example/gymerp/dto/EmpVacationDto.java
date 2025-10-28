package com.example.gymerp.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmpVacationDto {
	private int vacNum; // 휴가 번호 (PK)
	private int empNum; // 직원 번호 (FK)
	private Date vacStartedAt; // 휴가 시작일
	private Date vacEndedAt; // 휴가 종료일
	private String vacContent; // 휴가 내용
	private String vacState; // 휴가 상태
	private Integer earnedDays; // 발생 일수
	private Integer remainingDays; // 잔여 일수
	private Integer usedDays; // 사용 일수
}
