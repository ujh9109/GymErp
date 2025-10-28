package com.example.gymerp.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.gymerp.dto.SalesService;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ModalDaoImpl implements ModalDao {

    private final SqlSession session;

    /* ================================
       서비스 상품 선택 모달
    ================================= */

    // 서비스 목록 조회
    @Override
    public List<SalesService> selectServiceList(Map<String, Object> params) {
        return session.selectList("ModalMapper.selectServiceList", params);
    }

    // 서비스 선택 총 개수 조회
    @Override
    public int countServiceList(Map<String, Object> params) {
        return session.selectOne("ModalMapper.countServiceList", params);
    }

    /* ================================
       서비스 상품 선택 모달 끝
    ================================= */
}
