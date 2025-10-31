package com.example.gymerp.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.gymerp.dto.EmpDto;
import com.example.gymerp.repository.EmpDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	
	private final EmpDao empDao;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// 로그인용 DAO 메소드 (비밀번호 포함)
		EmpDto emp = empDao.selectAuthByEmail(email);
		if(emp == null) {
			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + email);
		}
		
		return new CustomUserDetails(
				emp.getEmpNum(),
				emp.getEmpName(),
				emp.getEmpEmail(),
				emp.getPassword(), // BCrypt 해시
				emp.getRole()
		);
	}
	
	
	
}
