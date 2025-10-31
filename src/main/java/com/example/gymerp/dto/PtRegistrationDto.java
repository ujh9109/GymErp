package com.example.gymerp.dto;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PtRegistrationDto {
    private int regNum;          // 예약 번호 (PK)
    private int empNum;          // 트레이너 번호 (FK → EMP)
    private int memNum;          // 회원 번호 (FK → MEMBER)
    private int shNum;           // 스케줄 번호 (FK → SCHEDULE)
    private Timestamp regCreated; // 예약 생성 일시
    private Date regTime;         // PT 시작 시간
    private Date lastTime;        // PT 종료 시간
    private Date regDate;         // 예약 일자
    private Timestamp regUpdatedAt; // 예약 수정 일시
    private String regNote;       // 비고
}
