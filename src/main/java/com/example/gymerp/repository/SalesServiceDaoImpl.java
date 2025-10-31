package com.example.gymerp.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.example.gymerp.dto.SalesService;

import lombok.RequiredArgsConstructor;

@Primary
@Repository
@RequiredArgsConstructor
public class SalesServiceDaoImpl implements SalesServiceDao {

    private final SqlSession sqlSession;

    // 전체 서비스 판매 내역 조회
    @Override
    public List<SalesService> selectAllSalesServices() {
        return sqlSession.selectList("SalesServiceMapper.selectAllSalesServices");
    }

    // 단일 서비스 판매 내역 조회
    @Override
    public SalesService selectSalesServiceById(long serviceSalesId) {
        return sqlSession.selectOne("SalesServiceMapper.selectSalesServiceById", serviceSalesId);
    }

    // 서비스 판매 등록
    @Override
    public int insertSalesService(SalesService salesService) {
        return sqlSession.insert("SalesServiceMapper.insertSalesService", salesService);
    }

    // 서비스 판매 수정
    @Override
    public int updateSalesService(SalesService salesService) {
        return sqlSession.update("SalesServiceMapper.updateSalesService", salesService);
    }

    // 서비스 판매 삭제 (논리 삭제)
    @Override
    public int deleteSalesService(long serviceSalesId) {
        return sqlSession.update("SalesServiceMapper.deleteSalesService", serviceSalesId);
    }

    // 서비스 판매 내역 조회 (페이지네이션 + 필터)
    @Override
    public List<SalesService> selectPagedServiceSales(Map<String, Object> param) {
        return sqlSession.selectList("SalesServiceMapper.selectPagedServiceSales", param);
    }

    // 전체 행 개수 조회 (페이지네이션용)
    @Override
    public int countPagedServiceSales(Map<String, Object> param) {
        return sqlSession.selectOne("SalesServiceMapper.countPagedServiceSales", param);
    }

    // 서비스 매출 그래프 조회
    @Override
    public List<Map<String, Object>> selectServiceSalesGraph(Map<String, Object> param) {
        return sqlSession.selectList("SalesServiceMapper.selectServiceSalesGraph", param);
    }
}
