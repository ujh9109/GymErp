package com.example.gymerp.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.gymerp.dto.EmpDto;

public interface EmpDao {
	// 전체 직원 목록 조회
	List<EmpDto> getAllEmp();
	
	// 직원 1명 조회
	EmpDto getEmpByNum(@Param("empNum") int empNum);
	
	// 직원 등록
	int insertEmp(EmpDto dto);
	
	// 직원 정보 수정
	int updateEmp(EmpDto dto);
	
	// 직원 삭제 
	int deleteEmp(EmpDto dto);
	
	// 직원 검색
	List<EmpDto> searchEmp(
			@Param("keyword") String keyword, 
			@Param("filter") String filter);
}
