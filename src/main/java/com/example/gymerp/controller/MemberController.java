package com.example.gymerp.controller;

import java.io.File;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.gymerp.dto.MemberDto;
import com.example.gymerp.service.MemberService;

import lombok.RequiredArgsConstructor;

/**
 * ✅ MemberController (회원 관리 컨트롤러)
 * - 프론트엔드 axios 요청과 매핑되는 API 엔드포인트 정의
 * - CRUD + 검색 + 프로필 업로드 기능 포함
 */
@RequestMapping("/v1")
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    // ✅ (1) 회원 전체 조회
    // 프론트 요청: GET /v1/member
    @GetMapping("/member")
    public List<MemberDto> list() {
        return memberService.getAllMembers();
    }

    // ✅ (2) 회원 상세 조회
    // 프론트 요청: GET /v1/member/{memNum}
    @GetMapping("/member/{memNum}")
    public MemberDto detail(@PathVariable int memNum) {
        return memberService.getMember(memNum);
    }

    // ✅ (3) 회원 등록
    // 프론트 요청: POST /v1/member
    @PostMapping("/member")
    public ResponseEntity<Void> create(@RequestBody MemberDto dto) {
        memberService.createMember(dto);
        return ResponseEntity.ok().build();
    }

    // ✅ (4) 회원 수정 (PUT 요청용)
    // 프론트 요청: PUT /v1/member/{memNum}
    @PutMapping("/member/{memNum}")
    public ResponseEntity<Void> updatePut(@PathVariable int memNum, @RequestBody MemberDto dto) {
        dto.setMemNum(memNum);
        memberService.updateMember(dto);
        return ResponseEntity.noContent().build();
    }

    // ✅ (5) 회원 수정 (PATCH 요청용)
    // 일부 필드만 변경할 때 사용 가능
    @PatchMapping("/member/{memNum}")
    public ResponseEntity<Void> updatePatch(@PathVariable int memNum, @RequestBody MemberDto dto) {
        dto.setMemNum(memNum);
        memberService.updateMember(dto);
        return ResponseEntity.noContent().build();
    }

    // ✅ (6) 회원 삭제
    // 프론트 요청: DELETE /v1/member/{memNum}
    @DeleteMapping("/member/{memNum}")
    public ResponseEntity<Void> delete(@PathVariable int memNum) {
        memberService.deleteMember(memNum);
        return ResponseEntity.noContent().build();
    }

    // ✅ (7) 회원 검색
    // 프론트 요청: GET /v1/member/search?keyword=값
    @GetMapping("/member/search")
    public List<MemberDto> search(@RequestParam String keyword) {
        return memberService.searchMembers(keyword);
    }

    // ✅ (8) 회원 프로필만 수정 (문자열 경로 기반)
    // 프론트 요청: PATCH /v1/member/{memNum}/profile
    @PatchMapping("/member/{memNum}/profile")
    public ResponseEntity<Void> updateProfile(@PathVariable int memNum, @RequestBody MemberDto dto) {
        memberService.updateMemberProfile(memNum, dto.getMemProfile());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/member/upload/{memNum}")
    public ResponseEntity<String> uploadMemberProfile(@PathVariable int memNum,
                                                      @RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("파일이 비어있습니다.");
        }
        try {
            // 1) 저장 경로
            String uploadDir = "C:/playground/final_project/GymErp/profile/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            // 2) 저장 파일명 생성 (확장자 유지)
            String original = file.getOriginalFilename();
            String ext = "";
            if (original != null && original.lastIndexOf('.') != -1) {
                ext = original.substring(original.lastIndexOf('.'));
            }
            String fileName = memNum + "_" + System.currentTimeMillis() + ext;

            // 3) 파일 저장
            File dest = new File(uploadDir + fileName);
            file.transferTo(dest);

            // 4) DB 업데이트
            memberService.updateMemberProfile(memNum, fileName);

            // 5) 프론트로 파일명 반환
            return ResponseEntity.ok(fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("업로드 실패: " + e.getMessage());
        }
    }
}
