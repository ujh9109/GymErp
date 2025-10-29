package com.example.gymerp.repository;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.example.gymerp.dto.SalesService;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SalesServiceDaoImpl implements SalesServiceDao {

    private final SqlSession session;
    

    // 전체 판매 내역 조회
    @Override
    public List<SalesService> selectAllSalesServices() {
        return session.selectList("SalesServiceMapper.selectAllSalesServices");
    }

    // 단일 판매 내역 조회
    @Override
    public SalesService selectSalesServiceById(Long serviceSalesId) {
        return session.selectOne("SalesServiceMapper.selectSalesServiceById", serviceSalesId);
    }

    // 판매 등록
    @Override
    public int insertSalesService(SalesService salesService) {
        return session.insert("SalesServiceMapper.insertSalesService", salesService);
    }

    // 판매 수정
    @Override
    public int updateSalesService(SalesService salesService) {
        return session.update("SalesServiceMapper.updateSalesService", salesService);
    }

    // 판매 삭제
    @Override
    public int deleteSalesService(Long serviceSalesId) {
        return session.update("SalesServiceMapper.deleteSalesService", serviceSalesId);
    }

    // 매출 내역 조회(페이지 내 스크롤 + 페이지네이션)
    @Override
    public List<SalesService> selectPagedServiceSales(Map<String, Object> params) {
        return session.selectList("SalesServiceMapper.selectPagedServiceSales", params);
    }

    // 페이지네이션 총 개수 조회
    @Override
    public int countPagedServiceSales(Map<String, Object> params) {
        return session.selectOne("SalesServiceMapper.countPagedServiceSales", params);
    }

    // 매출 그래프 조회
    @Override
    public List<Map<String, Object>> selectServiceSalesGraph(Map<String, Object> params) {
        return session.selectList("SalesServiceMapper.selectServiceSalesGraph", params);
    }
}
