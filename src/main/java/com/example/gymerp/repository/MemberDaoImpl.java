package com.example.gymerp.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.gymerp.dto.MemberDto;

import lombok.RequiredArgsConstructor;


//주석
@Repository
@RequiredArgsConstructor
public class MemberDaoImpl implements MemberDao {
	
	private final SqlSession session;

	// 회원 상세 조회
	@Override
	public MemberDto getByNum(int memNum) {
		return session.selectOne("MemberMapper.selectMemberById", memNum);
	}

	@Override
	public List<MemberDto> selectAll() {
		return session.selectList("MemberMapper.selectAllMembers");
	}

	@Override
	public void insert(MemberDto dto) {
		session.insert("MemberMapper.insertMember", dto);
	}

	@Override
	public int delete(int memNum) {
		return session.delete("MemberMapper.deleteMember", memNum);
	}

	@Override
	public int update(MemberDto dto) {
		return session.update("MemberMapper.updateMember", dto);
	}
	
	

}
