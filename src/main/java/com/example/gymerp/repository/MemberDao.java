package com.example.gymerp.repository;

import java.util.List;

import com.example.gymerp.dto.MemberDto;

//주석
public interface MemberDao {
	
	public List<MemberDto> selectAll(); // 전체 회원 조회 
	
	public MemberDto getByNum(int memNum); // 회원 상세 조회 
	
	public void insert(MemberDto dto); // 회원 등록 
	public int delete(int memNum); // 회원 삭제 
	public int update(MemberDto dto); // 회원 전체 수정 
	
	// ▼ 추가: 프로필 이미지 수정
	public int updateProfile(int memNum, String memProfile);
	// 검색
	List<MemberDto> search(String keyword);  
}
