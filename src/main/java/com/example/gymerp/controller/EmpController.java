package com.example.gymerp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gymerp.dto.EmpDto;
import com.example.gymerp.security.CustomUserDetails;
import com.example.gymerp.service.EmpService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1") 
public class EmpController {

    private final EmpService empService;
    public final AuthenticationManager authManager;

    // 전체 직원 목록 조회 api
    @GetMapping("/list")
    public List<EmpDto> getEmpList() {
        return empService.getAllEmp();
    }
    
    // 직원 상세 조회
    @GetMapping("/{empNum}")
    public EmpDto getEmp(@PathVariable int empNum) {
        return empService.getEmpByNum(empNum);
    }

    // 직원 등록
    @PostMapping
    public String insert(@RequestBody EmpDto dto) {
        empService.insertEmp(dto);
        return "success";
    }

    // 직원 수정
    @PutMapping("/{empNum}")
    public String update(@PathVariable int empNum, @RequestBody EmpDto dto) {
        dto.setEmpNum(empNum);
        empService.updateEmp(dto);
        return "success";
    }

    // 직원 삭제
    @DeleteMapping("/{empNum}")
    public String delete(@PathVariable int empNum) {
        EmpDto dto = new EmpDto();
        dto.setEmpNum(empNum);
        empService.deleteEmp(dto);
        return "success";
    }
    
    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody EmpDto dto, HttpServletRequest request){
    	// AuthenticationManager 로 인증 시도 (Security 내부에서 UserDetailService 호출)
		Authentication auth = authManager.authenticate(
    			new UsernamePasswordAuthenticationToken(dto.getEmpEmail(), dto.getPassword()));
    	
		// 인증 결과를 SecurityContext 에 저장 (세션에도 연동)
    	SecurityContextHolder.getContext().setAuthentication(auth);
    	HttpSession session = request.getSession(true); // true -> 없으면 새로 생성
    	
    	// 로그인 성공 시 사용자 정보 반환
    	CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
    	Map<String, Object> res = new HashMap<>();
    	res.put("empNum", user.getEmpNum());
        res.put("empName", user.getEmpName());
        res.put("email", user.getUsername());
        res.put("role", user.getRole());
        res.put("sessionId", session.getId());
        
        return ResponseEntity.ok(res);
    	
    }
    
    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
    	HttpSession session = request.getSession(false);
    	if(session != null) session.invalidate();
    	SecurityContextHolder.clearContext();
    	
    	return ResponseEntity.ok(Map.of("message", "로그아웃 완료"));
    }
    
}
