package com.example.gymerp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.gymerp.dto.MemberDto;
import com.example.gymerp.repository.MemberDao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
	
	private final MemberDao memberDao;
	
	@Override
	public void createMember(MemberDto dto) {
		
		memberDao.insert(dto);
	}

	@Override
	public void updateMember(MemberDto dto) {
		
		int rowCount = memberDao.update(dto);
	}

	@Override
	public void deleteMember(int memNum) {
		
		int rowCount = memberDao.delete(memNum);
	}

	@Override
	public MemberDto getMember(int memNum) {
		
		return memberDao.getByNum(memNum);
	}

	@Override
	public List<MemberDto> getAllMembers() {
		
		return memberDao.selectAll();
	}

}
