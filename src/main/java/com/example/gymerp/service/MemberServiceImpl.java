package com.example.gymerp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.gymerp.dto.MemberDto;
import com.example.gymerp.repository.MemberDao;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	
	final private MemberDao memberDao;
	
	// 회원추가
	@Override
	public int addMember(MemberDto dto) {
		
		return memberDao.createMember(dto);
	}
	
	// 회원 수정
	@Override
	public int updateMember(MemberDto dto) {
		
		return memberDao.updateMember(dto);
	}
	
	// 회원삭제
	@Override
	public int deleteMember(int memNum) {
		
		return memberDao.deleteMember(memNum);
	}
	
	// 회원 한명조회
	@Override
	public MemberDto getMember(int memNum) {
		
		return memberDao.getMemberById(memNum);
	}
	
	// 회원 전체조회
	@Override
	public List<MemberDto> getAllMembers() {
		
		return memberDao.getMemberList();
	}
	
	
}
