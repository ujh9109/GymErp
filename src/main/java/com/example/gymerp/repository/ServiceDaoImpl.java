package com.example.gymerp.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ServiceDaoImpl {

	//의존 객체
	private final SqlSession session;
	
	
}
