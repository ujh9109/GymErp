package com.example.gymerp.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.gymerp.dto.CodeDto;
import com.example.gymerp.dto.ProductDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ProductDaoImpl implements ProductDao{
	
	//의존 객체
	private final SqlSession session;

	@Override
	public List<ProductDto> selectPage(ProductDto dto) {
		
		return session.selectList("product.selectPage", dto);
	}

	@Override
	public int getCount(ProductDto dto) {
		
		return session.selectOne("product.getCount", dto);
	}

	@Override
	public List<CodeDto> getCodeList(CodeDto dto) {
		
		return session.selectList("product.getCodeList", dto);
	}

	@Override
	public void insert(ProductDto dto) {
		session.insert("product.insert", dto);
		
	}	
}
