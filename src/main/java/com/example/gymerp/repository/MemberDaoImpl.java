package com.example.gymerp.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	// 전체 회원 조회
	@Override
	public List<MemberDto> selectAll() {
		return session.selectList("MemberMapper.selectAllMembers");
	}

	// 회원 등록
	@Override
	public void insert(MemberDto dto) {
		session.insert("MemberMapper.insertMember", dto);
	}

	// 회원 삭제 (소프트 삭제로 동작 가능)
	@Override
	public int delete(int memNum) {
		return session.update("MemberMapper.deleteMember", memNum);
	}

	// 회원 전체 수정
	@Override
	public int update(MemberDto dto) {
		return session.update("MemberMapper.updateMember", dto);
	}

	// ▼ 추가: 프로필 이미지 수정
	@Override
	public int updateProfile(int memNum, String memProfile) {
	    MemberDto dto = new MemberDto();
	    dto.setMemNum(memNum);
	    dto.setMemProfile(memProfile);
	    return session.update("MemberMapper.updateMemberProfile", dto);
	}

	@Override
	public List<MemberDto> search(String keyword) {
	    Map<String, Object> param = new HashMap<>();
	    param.put("keyword", keyword);
	    return session.selectList("MemberMapper.searchMembers", param);
	}
}
