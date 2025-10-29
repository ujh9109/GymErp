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
}