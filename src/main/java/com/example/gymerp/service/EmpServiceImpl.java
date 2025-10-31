package com.example.gymerp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.gymerp.dto.EmpDto;
import com.example.gymerp.repository.EmpDao;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpServiceImpl implements EmpService {
	private final EmpDao empDao;
	
	// 전체 직원 목록 조회
	@Override
	public List<EmpDto> getAllEmp() {
	return empDao.getAllEmp();
	}
	
	// 직원 1명 조회
	@Override
	public EmpDto getEmpByNum(int empNum) {
	    return empDao.getEmpByNum(empNum);
	}
	
	// 직원 등록
	@Override
	public int insertEmp(EmpDto dto) {
		return empDao.insertEmp(dto);
	}
	
	// 직원 정보 수정
	@Override
	public int updateEmp(EmpDto dto) {
		return empDao.updateEmp(dto);
	}
	
	// 직원 삭제
	@Override
	public int deleteEmp(EmpDto dto) {
		return empDao.deleteEmp(dto);
	}
	
	// 직원 검색
	@Override
	public List<EmpDto> searchEmp(String keyword, String filter) {
		return empDao.searchEmp(keyword, filter);
	}
	
	// 직원 검색 + 페이징
	@Override
	public List<EmpDto> getEmpListPaged(String type, String keyword, int start, int end) {
	    return empDao.getEmpListPaged(type, keyword, start, end);
	}

	// 직원 총 개수
	@Override
	public int getTotalCount(String type, String keyword) {
	    return empDao.getTotalCount(type, keyword);
	}

	@Override
	public void updateProfileImage(int empNum, String fileName) {
		empDao.updateProfileImage(empNum, fileName);
		
	}
}
