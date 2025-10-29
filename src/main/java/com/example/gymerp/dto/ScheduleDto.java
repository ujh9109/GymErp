package com.example.gymerp.dto;

import java.time.LocalDateTime;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Alias("Schedule")
@Builder 
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ScheduleDto {
	private int shNum;  					// 스케술 고유 번호
	private int empNum; 					// 직원 고유 번호(PK -> FK)
	private LocalDateTime startTime; 		// 시작 시간
	private LocalDateTime endTime; 			// 끝나는 시간
	private String memo; 					// 메모
	private String color; 					// 캘린더 표시 색상
	private String refType; 				// 참조 구분
	private int refId; 						// 참조 번호
}
