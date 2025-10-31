package com.example.gymerp.service;

import java.util.List;

import com.example.gymerp.dto.CodeDto;

public interface CategoryService {
	public List<CodeDto> getAllCodes(CodeDto dto);
	public void save(CodeDto dto);
}
