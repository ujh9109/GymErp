	package com.example.gymerp.dto;

import java.sql.Date;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
	
	   // 승인/반려만 사용
    @NotBlank
    @Pattern(regexp = "APPROVED|REJECTED")
	private String vacState; // 휴가 상태
    
    
	private Integer earnedDays; // 발생 일수
	private Integer remainingDays; // 잔여 일수
	private Integer usedDays; // 사용 일수
	
	 // 시작 <= 종료 검증
    @AssertTrue(message = "시작일은 종료일보다 늦을 수 없습니다.")
    public boolean isValidRange() {
        if (vacStartedAt == null || vacEndedAt == null) return true;
        return !vacStartedAt.after(vacEndedAt);
    }
}
