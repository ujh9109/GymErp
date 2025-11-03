package com.example.gymerp.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.example.gymerp.dto.EmpDto;
import com.example.gymerp.dto.ProductDto;
import com.example.gymerp.dto.ServiceDto;
import lombok.RequiredArgsConstructor;

@Primary
@Repository
@RequiredArgsConstructor
public class ModalDaoImpl implements ModalDao {

    private final SqlSession sqlSession;

    /* ================================
       서비스 상품 선택 모달
    ================================= */

    // 서비스 상품 목록 조회
    @Override
    public List<ServiceDto> getServiceModalList(ServiceDto dto) {
        return sqlSession.selectList("ModalMapper.getServiceModalList", dto);
    }

    // 서비스 상품 전체 개수 조회
    @Override
    public int getServiceModalCount(ServiceDto dto) {
        return sqlSession.selectOne("ModalMapper.getServiceModalCount", dto);
    }

    /* ================================
       서비스 상품 선택 모달 끝
    ================================= */
    
    
    
    
    
    /* ================================ 실물 상품 선택 모달 ================================= */
    
    
    @Override
    public List<ProductDto> getProductModalList(Map<String, Object> param) {
        return sqlSession.selectList("ModalMapper.getProductModalList", param);
    }

    @Override
    public int getProductModalCount(Map<String, Object> param) {
        return sqlSession.selectOne("ModalMapper.getProductModalCount", param);
    }

    /* ================================ 실물 상품 선택 모달 ================================= */
    
    
    
    /* ================================ 직원 선택 모달 ================================= */

	
	@Override
	public List<EmpDto> getEmployeeModalList(Map<String, Object> param) {
	return sqlSession.selectList("ModalMapper.getEmployeeModalList", param);
	}
	
	@Override
	public int getEmployeeModalCount(Map<String, Object> param) {
	return sqlSession.selectOne("ModalMapper.getEmployeeModalCount", param);
	}
	
    /* ================================ 직원 선택 모달 끝 ================================= */
    
}
