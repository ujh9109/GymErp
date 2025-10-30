package com.example.gymerp.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.gymerp.dto.CodeDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CategoryDaoImpl implements CategoryDao{
	//의존 객체
	private final SqlSession session;
	
	@Override
	public List<CodeDto> getCodeList(CodeDto dto) {
		
		return session.selectList("category.getCodeList", dto);
	}
}
