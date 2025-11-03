package com.example.gymerp.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleDto {
	private Integer shNum; // PK
	private Integer empNum; // FK → Employee.empNum
	private LocalDateTime startTime; // ← Timestamp → LocalDateTime
    private LocalDateTime endTime;   // ← Timestamp → LocalDateTime
	private String memo; // VARCHAR2(200)
	private String refType; // PT | VACATION | ETC ...
}
