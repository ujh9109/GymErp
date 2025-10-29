package com.example.gymerp.service;

import java.util.List;

import com.example.gymerp.dto.MemberDto;

public interface MemberService {
	// 회원 추가
    int addMember(MemberDto dto);

    // 회원 수정
    int updateMember(MemberDto dto);

    // 회원 삭제
    int deleteMember(int memNum);

    // 회원 상세 조회
    MemberDto getMember(int memNum);

    // 회원 전체 조회
    List<MemberDto> getAllMembers();

}
