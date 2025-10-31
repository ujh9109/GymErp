package com.example.gymerp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gymerp.dto.CodeDto;
import com.example.gymerp.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/v1")
@RestController
@RequiredArgsConstructor
public class CategoryController {
	
	private final CategoryService categoryService;

	//상품 분류 목록 조회
	@GetMapping("/categories/list")
	public List<CodeDto> getCategoryList(CodeDto dto) {
		
		return categoryService.getAllCodes(dto);
	}
	
	//상품 분류 추가
	@PostMapping("/categories")
	public void createCategory(@RequestBody CodeDto dto) {
		categoryService.save(dto);
	}
}
