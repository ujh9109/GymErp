package com.example.gymerp.dto;

import java.time.LocalDateTime;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Alias("ScheduleDto")
@Builder 
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ScheduleDto {
	private int shNum;  					// 스케술 고유 번호
	private int empNum; 					// 직원 고유 번호(PK -> FK)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime startTime; 		// 시작 시간
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime endTime; 			// 끝나는 시간
	private String memo; 					// 메모
	private String color; 					// 캘린더 표시 색상

	
}
