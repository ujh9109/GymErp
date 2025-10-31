package com.example.gymerp.service;

import java.util.List;
import com.example.gymerp.dto.EmpDto;

public interface EmpService {
	
	// 전체 직원 목록 조회
    List<EmpDto> getAllEmp();
    
    // 직원 1명 조회
    EmpDto getEmpByNum(int empNum);
    
    // 직원 등록
    int insertEmp(EmpDto dto);
    
    // 직원 정보 수정
    int updateEmp(EmpDto dto);
    
    // 직원 삭제 
    int deleteEmp(EmpDto dto);
    
    // 직원 검색
    List<EmpDto> searchEmp(String keyword, String filter);
    
    // 로그인 (이메일 + 비밀번호)
    EmpDto login(String email, String password);
    
    // 비밀번호 변경 (선택)
    int updatePassword(int empNum, String newPassword);
    // 직원 검색 + 페이징
    List<EmpDto> getEmpListPaged(String type, String keyword, int start, int end);

    // 직원 총 개수
    int getTotalCount(String type, String keyword);
    
    // 프로필이미지
    void updateProfileImage(int empNum, String fileName);
}