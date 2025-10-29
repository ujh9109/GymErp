package com.example.gymerp.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.gymerp.dto.ServiceDto;

import lombok.RequiredArgsConstructor;

// 모달 데이터 조회 DAO 구현체 (MyBatis 연동)
@Repository
@RequiredArgsConstructor
public class ModalDaoImpl implements ModalDao {

    private final SqlSession sqlSession;
    

    /* ================================
       서비스 상품 선택 모달
    ================================= */

    // 서비스 상품 목록 조회
    @Override
    public List<ServiceDto> getServiceModalList(Map<String, Object> param) {
        return sqlSession.selectList("ModalMapper.getServiceModalList", param);
    }

    // 서비스 상품 전체 개수 조회
    @Override
    public int getServiceModalCount(Map<String, Object> param) {
        return sqlSession.selectOne("ModalMapper.getServiceModalCount", param);
    }

    /* ================================
       서비스 상품 선택 모달 끝
    ================================= */
}
