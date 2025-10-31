package com.example.gymerp.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.gymerp.service.EmpService;

import lombok.RequiredArgsConstructor;

import com.example.gymerp.dto.EmpDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/emp") 
public class EmpController {

    private final EmpService empService;

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
    
    // 직원 검색 + 페이징
    @GetMapping("/list/paging")
    public Map<String, Object> getEmpListPaged(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "all") String type,
            @RequestParam(required = false) String keyword
    ) {
        Map<String, Object> result = new HashMap<>();

        int totalCount = empService.getTotalCount(type, keyword);
        int start = (page - 1) * size + 1;
        int end = page * size;

        List<EmpDto> list = empService.getEmpListPaged(type, keyword, start, end);
        int totalPage = (int) Math.ceil((double) totalCount / size);

        result.put("list", list);
        result.put("page", page);
        result.put("size", size);
        result.put("totalCount", totalCount);
        result.put("totalPage", totalPage);

        return result;
    }
    
    // 프로필이미지 업로드
    @PostMapping("/upload/{empNum}")
    public ResponseEntity<String> uploadProfile(
            @PathVariable int empNum,
            @RequestParam("file") MultipartFile file) {

        try {
            // 업로드 폴더 경로 지정 (운영 시 절대경로로 수정)
            String uploadDir = "C:/playground/final_project/GymErp/profile/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            // 저장 파일명 (중복 방지)
            String fileName = empNum + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            // 파일 저장
            File dest = new File(uploadDir + fileName);
            file.transferTo(dest);

            // DB에 파일명 저장
            empService.updateProfileImage(empNum, fileName);

            // 프론트로 반환 (React에서 미리보기용)
            return ResponseEntity.ok(fileName);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("업로드 실패: " + e.getMessage());
        }
    }

}
