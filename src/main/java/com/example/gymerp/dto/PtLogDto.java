package com.example.gymerp.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Alias("PtLogDto")
@Builder 
@NoArgsConstructor 
@AllArgsConstructor
@Data
public class PtLogDto {
	private Long usageId;        // 이용내역 고유번호 (PK)
    private Long memNum;         // 회원 번호 (FK)
    private Long empNum;         // 트레이너(직원) 번호 (FK)
    private String trainerName;  // 트레이너 이름
    private String memberName;   // 회원 이름
    private String status;       // 상태 (ex: 결제, 차감 등)
    private Integer countChange; // 이용횟수 변화량
    private Integer totalAmount; // 총 남은 횟수
    private Integer consumeAmount; // 소비 금액
    private String createdAt;    // 생성일자
}
