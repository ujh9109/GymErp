package com.example.gymerp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gymerp.dto.MemberDto;
import com.example.gymerp.service.MemberService;

import lombok.RequiredArgsConstructor;

//주석
@RequestMapping("/v1")
@RequiredArgsConstructor
@RestController
public class MemberController {
	
	private final MemberService memberService;
	
	@GetMapping("/member")
	public List<MemberDto> list(){
		return memberService.getAllMembers();
	}
	
	@PostMapping("/member")
	public ResponseEntity<Void> create(@RequestBody MemberDto dto) {
		memberService.createMember(dto);
		
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/member/{memNum}")
	public ResponseEntity<Void> delete(@PathVariable int memNum){
		memberService.deleteMember(memNum);
		
		return ResponseEntity.noContent().build();
	}
	
	@PatchMapping("/member/{memNum}")
	public ResponseEntity<Void> update(@PathVariable int memNum, @RequestBody MemberDto dto){
		memberService.updateMember(dto);
		
		return ResponseEntity.noContent().build();
	}
	
} 
