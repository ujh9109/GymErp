package com.example.gymerp.controller;

import java.net.URI;
import java.util.List;

import org.apache.ibatis.javassist.compiler.ast.Keyword;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gymerp.dto.MemberDto;
import com.example.gymerp.service.MemberService;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/v1/member")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

    // 회원 추가
    @PostMapping
    public ResponseEntity<MemberDto> createMember(@RequestBody MemberDto dto) {
        memberService.addMember(dto);
        // dto에 memNum이 selectKey로 채워졌다고 가정
        return ResponseEntity.created(URI.create("/v1/member/" + dto.getMemNum()))
                             .body(dto);
    }

    // 전체 회원 조회
    @GetMapping
    public ResponseEntity<List<MemberDto>> getMemberList() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    // 회원 1명 조회
    @GetMapping("/{memNum}")
    public ResponseEntity<MemberDto> getMemberById(@PathVariable int memNum) {
        return ResponseEntity.ok(memberService.getMember(memNum));
    }

    // 회원 삭제
    @DeleteMapping("/{memNum}")
    public ResponseEntity<Void> deleteMember(@PathVariable int memNum) {
        memberService.deleteMember(memNum);
        return ResponseEntity.noContent().build();
    }

    // 회원 정보 수정 (PUT)
    @PutMapping("/{memNum}")
    public ResponseEntity<MemberDto> updateMember(@PathVariable int memNum, @RequestBody MemberDto dto) {
        dto.setMemNum(memNum);
        memberService.updateMember(dto);
        return ResponseEntity.ok(dto);
    }

}
