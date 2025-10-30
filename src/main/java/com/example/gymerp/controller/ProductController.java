package com.example.gymerp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public ProductListResponse getProductList(@RequestParam(defaultValue = "1") int pageNum, ProductDto dto) {
		
		return productService.getProducts(pageNum, dto);
	}
		
	//실물 상품 등록
	@PostMapping("/product")
	public void createProduct(@RequestBody ProductDto dto) {
		
		productService.save(dto);
	}
	
	//실물 상품 수정
	@PutMapping("/product/{productId}")
	public ProductDto updateProduct(@RequestBody ProductDto dto, @PathVariable int productId) {
		dto.setProductId(productId);
		//path variable 이 없는 방식(dto 에 product id 를 담아서 보내주는 방식)을 시도해보고 된다면 path variable 지우기
		productService.modifyProduct(dto);
		
		return dto;
	}
	
	//실물 상품 상세정보 조회
	@GetMapping("/product/{productId}")
	public ProductDto getProductDetail(@PathVariable int productId) {
		ProductDto result = productService.getDetail(productId);
		
		return result;
	}
	
	//실물 상품 판매 상태 변경
	@PatchMapping("/product/{productId}")
	public ResponseEntity<Void> updateProductStatus(
			@PathVariable int productId, 
            @RequestBody ProductDto dto
			){
		if (dto.getIsActive() == null) {
            return ResponseEntity.badRequest().build(); // 간단한 유효성 검사
        }
		
		productService.updateProductStatus(productId, dto.getIsActive());
		
		return ResponseEntity.ok().build();
	}
}
