package com.example.gymerp.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.gymerp.dto.MemberDto;

import lombok.RequiredArgsConstructor;


@Repository
@RequiredArgsConstructor
public class MemberDaoImpl implements MemberDao {
	
	final private SqlSession session;

	// 회원 추가
	@Override
	public int createMember(MemberDto dto) {
		return session.insert("MemberMapper.insertMember", dto);
	}

	// 회원 전체 조회
	@Override
	public List<MemberDto> getMemberList() {
		return session.selectList("MemberMapper.selectAllMembers");
	}

	// 회원 상세 조회
	@Override
	public MemberDto getMemberById(int memNum) {
		return session.selectOne("MemberMapper.selectMemberById", memNum);
	}

	// 회원 삭제
	@Override
	public int deleteMember(int memNum) {
		return session.delete("MemberMapper.deleteMember", memNum);
	}

	// 회원 수정
	@Override
	public int updateMember(MemberDto dto) {
		return session.update("MemberMapper.updateMember", dto);
	}
	
	

}
