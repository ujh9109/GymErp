package com.example.gymerp.repository;

import java.util.List;

import com.example.gymerp.dto.CodeDto;
import com.example.gymerp.dto.ProductDto;

public interface ProductDao {

	public List<ProductDto> selectPage(ProductDto dto);
	public int getCount(ProductDto dto);
	public List<CodeDto> getCodeList(CodeDto dto);
	public void insert(ProductDto dto);
}
