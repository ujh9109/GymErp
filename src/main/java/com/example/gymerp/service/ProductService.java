package com.example.gymerp.service;

import java.util.List;

import com.example.gymerp.dto.CodeDto;
import com.example.gymerp.dto.ProductDto;
import com.example.gymerp.dto.ProductListResponse;

public interface ProductService {

	public ProductListResponse getProductList(int pageNum, ProductDto dto);
	public List<CodeDto> getAllCodes(CodeDto dto);
	public void createProduct(ProductDto dto);
}
