package com.example.gymerp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.gymerp.dto.CodeDto;
import com.example.gymerp.dto.ProductDto;
import com.example.gymerp.dto.ProductListResponse;
import com.example.gymerp.service.ProductService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/v1")
@RestController
@RequiredArgsConstructor
public class ProductController {
	
	private final ProductService productService;
	
	//상품 목록 조회
	@GetMapping("/product")
	public ProductListResponse list(@RequestParam(defaultValue = "1") int pageNum, ProductDto dto) {
		
		return productService.getProductList(pageNum, dto);
	}
	
	//상품 분류 목록 조회
	@GetMapping("/categories/list")
	public List<CodeDto> codeList(CodeDto dto) {
		
		return productService.getAllCodes(dto);
	}
	
	//실물 상품 등록
	@PostMapping("/product")
	public void save(@RequestBody ProductDto dto) {
		
		productService.createProduct(dto);
	}
	
}
