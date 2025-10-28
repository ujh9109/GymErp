package com.example.gymerp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gymerp.dto.SalesItem;
import com.example.gymerp.repository.SalesItemDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SalesItemServiceImpl implements SalesItemService {

    private final SalesItemDao salesItemDao;

    // 상품 판매 내역 전체 조회
    @Override
    public List<SalesItem> getAllSalesItems() {
        return salesItemDao.selectAllSalesItems();
    }

    // 상품 판매 내역 단일 조회
    @Override
    public SalesItem getSalesItemById(Long itemSalesId) {
        return salesItemDao.selectSalesItemById(itemSalesId);
    }

    // 상품 판매 내역 등록
    @Override
    public int addSalesItem(SalesItem salesItem) {
        return salesItemDao.insertSalesItem(salesItem);
    }

    // 상품 판매 내역 수정
    @Override
    public int updateSalesItem(SalesItem salesItem) {
        return salesItemDao.updateSalesItem(salesItem);
    }

    // 상품 판매 내역 삭제 (status = 'DELETED')
    @Override
    public int deleteSalesItem(Long itemSalesId) {
        return salesItemDao.deleteSalesItem(itemSalesId);
    }
}
