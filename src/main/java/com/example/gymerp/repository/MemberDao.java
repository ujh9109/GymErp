package com.example.gymerp.repository;

import java.util.List;

import com.example.gymerp.dto.MemberDto;

public interface MemberDao {
	int createMember(MemberDto dto);        // 회원 등록 
    List<MemberDto> getMemberList();        // 전체 회원 조회 
    MemberDto getMemberById(int memNum);    // 회원 상세 조회 
    int deleteMember(int memNum);           // 회원 삭제 
    int updateMember(MemberDto dto);        // 회원 전체 수정 
    
}
