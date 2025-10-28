package com.example.gymerp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gymerp.dto.SalesService;
import com.example.gymerp.repository.SalesServiceDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SalesServiceServiceImpl implements SalesServiceService {

    private final SalesServiceDao salesServiceDao;

    // 서비스 판매 내역 전체 조회
    @Override
    public List<SalesService> getAllSalesServices() {
        return salesServiceDao.selectAllSalesServices();
    }

    // 서비스 판매 내역 단일 조회
    @Override
    public SalesService getSalesServiceById(Long serviceSalesId) {
        return salesServiceDao.selectSalesServiceById(serviceSalesId);
    }

    // 서비스 판매 내역 등록
    @Override
    public int addSalesService(SalesService salesService) {
        return salesServiceDao.insertSalesService(salesService);
    }

    // 서비스 판매 내역 수정
    @Override
    public int updateSalesService(SalesService salesService) {
        return salesServiceDao.updateSalesService(salesService);
    }

    // 서비스 판매 내역 삭제 (status = 'DELETED')
    @Override
    public int deleteSalesService(Long serviceSalesId) {
        return salesServiceDao.deleteSalesService(serviceSalesId);
    }
}
