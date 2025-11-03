package com.example.gymerp.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;


import com.example.gymerp.dto.PostDto;

import lombok.RequiredArgsConstructor;


@Repository
@RequiredArgsConstructor
public class PostDaoImpl implements PostDao {
	
	private final SqlSession session;
	private static final String NS = "com.example.gymerp.repository.PostDao"; // XML namespace

    @Override
    public List<PostDto> selectAll() {
        return session.selectList(NS + ".selectAll");
    }

    @Override
    public PostDto get(int postId) {
        return session.selectOne(NS + ".get", postId);
    }

    @Override
    public int insert(PostDto dto) {
        return session.insert(NS + ".insert", dto);
    }

    @Override
    public int update(PostDto dto) {
        return session.update(NS + ".update", dto);
    }

    @Override
    public int delete(int postId) {
        return session.delete(NS + ".delete", postId);
    }

    @Override
    public int incView(int postId) {
        return session.update(NS + ".incView", postId);
    }
	

}
