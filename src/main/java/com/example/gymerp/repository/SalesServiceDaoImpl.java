package com.example.gymerp.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.gymerp.dto.SalesService;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SalesServiceDaoImpl implements SalesServiceDao {

    private final SqlSession session;

    // 전체 서비스 판매 내역 조회
    @Override
    public List<SalesService> selectAllSalesServices() {
        return session.selectList("SalesServiceMapper.selectAllSalesServices");
    }

    // 단일 서비스 판매 내역 조회
    @Override
    public SalesService selectSalesServiceById(Long serviceSalesId) {	
        return session.selectOne("SalesServiceMapper.selectSalesServiceById", serviceSalesId);
    }

    // 서비스 판매 내역 등록
    @Override
    public int insertSalesService(SalesService salesService) {
        return session.insert("SalesServiceMapper.insertSalesService", salesService);
    }

    // 서비스 판매 내역 수정
    @Override
    public int updateSalesService(SalesService salesService) {
        return session.update("SalesServiceMapper.updateSalesService", salesService);
    }

    // 서비스 판매 내역 삭제 (status = 'DELETED')
    @Override
    public int deleteSalesService(Long serviceSalesId) {
        return session.update("SalesServiceMapper.deleteSalesService", serviceSalesId);
    }
}
