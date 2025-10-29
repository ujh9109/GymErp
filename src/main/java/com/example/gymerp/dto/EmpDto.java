package com.example.gymerp.dto;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("EmpDto")
public class EmpDto {
	private int empNum;           // 직원 고유번호 (PK)
    private String empName;       // 이름
    private String gender;        // 성별
    private String empAddress;    // 주소
    private Date empBirth;        // 생년월일
    private String empPhone;      // 연락처
    private String empEmail;      // 이메일
    private Date hireDate;        // 입사일
    private Date fireDate;        // 퇴사(예정)일
    private String profileImage;  // 프로필 사진 파일명
    private String empMemo;       // 메모
    private String role;          // 직급 (EMP, ADMIN)
}
