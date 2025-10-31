package com.example.gymerp.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Alias("EmpScheduleDto")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmpScheduleDto {
	private int calNum;         // 달력 고유번호 (PK)
    private int empNum;         // 직원 고유번호 (FK)
    private Integer shNum;        // 스케줄 고유번호 (FK)  
    private String color;       // 캘린더 표시 색상
    private String refType;     // 참조유형 (registration / vacation / etc)
    private int refId;          // 참조테이블 PK
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime date;     // 근무날짜
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startTime;  // 시작시간
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endTime;    // 종료시간
    private String memo;              // 메모
    private String empEmail;          // JOIN된 직원 이메일
    private String empName; 		// JOIN된 직원 이름

    // 상세 연계 객체
    private PTreservationDto registration; // PT / 수업 예약 상세
    private EmpVacationDto vacation;       // 휴가 상세
    private EtcDto etc;                    // 기타 일정 상세  // etc 상세
	

}
