package com.example.gymerp.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.gymerp.dto.EmpDto;
import com.example.gymerp.repository.EmpDao;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpServiceImpl implements EmpService {
	
	private final EmpDao empDao;
	private final PasswordEncoder passwordEncoder; // BCrypt
	
	// 전화번호 숫자만 남기기
	private String normalizePhone(String phone) {
		return phone == null ? null : phone.replaceAll("\\D", "");
	}
	
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
		// 전화번호 정규화 (010-0000-0000 -> 01000000000)
		String digits = normalizePhone(dto.getEmpPhone());
		dto.setEmpPhone(digits);
		
		// 비밀번호는 전화번호 뒤 4자리 자동 설정
		if(digits != null && digits.length() >= 4) {
			String rawPwd = digits.substring(digits.length() - 4);
			dto.setPassword(passwordEncoder.encode(rawPwd));
		} else {
			throw new IllegalArgumentException("유효하지 않은 전화번호 방식입니다");
		}
		
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

	@Override
	public EmpDto login(String email, String password) {
		EmpDto emp = empDao.selectAuthByEmail(email);
		
		if(emp == null) {
			throw new IllegalArgumentException("이메일이 존재하지 않습니다");
		}
		if(!passwordEncoder.matches(password, emp.getPassword())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
		}
		
		emp.setPassword(null); // 보안상 비밀번호 제거
		return emp;
	}

	@Override
	public int updatePassword(int empNum, String newPassword) {
		String hash = passwordEncoder.encode(newPassword);
		
		
		return empDao.updatePassword(empNum, hash);
	}
	
}
