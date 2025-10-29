package com.example.gymerp.dto;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberDto {
	 private int memNum;              // 회원 고유번호 (PK)asasadad
	 private String memName;          // 회원 이름
	 private String memGender;        // 성별
	 private Date memBirthday;        // 생일
	 private String memPhone;         // 연락처
	 private String memEmail;         // 이메일
	 private String memAddr;          // 주소
	 private Timestamp memCreated;    // 등록일
	 private Timestamp memUpdated;    // 수정일
	 private String memProfile;       // 프로필 이미지
	 private String memNote;          // 메모

}
