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
	
	
	/** 이용내역 DB 관련 내용입니다.*/

    // 회원 이름 단건 조회 (로그용)
    @Override
    public String selectMemberNameById(int memNum) {
        return session.selectOne("MemberMapper.selectMemberNameById", memNum);
    }

    // 회원 존재 여부 확인 (유효성 검증용)
    @Override
    public int checkMemberExists(int memNum) {
        return session.selectOne("MemberMapper.checkMemberExists", memNum);
    }

	@Override
	public int createMember(MemberDto dto) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<MemberDto> getMemberList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MemberDto getMemberById(int memNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteMember(int memNum) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateMember(MemberDto dto) {
		// TODO Auto-generated method stub
		return 0;
	}
}
