package com.example.gymerp.repository;

import java.util.List;

import com.example.gymerp.dto.CodeDto;

public interface CategoryDao {
	public List<CodeDto> getCodeList(CodeDto dto);
	public void insert(CodeDto dto);
}
