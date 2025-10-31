package com.example.gymerp.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.gymerp.dto.CurrentStockDto;
import com.example.gymerp.dto.PurchaseDto;
import com.example.gymerp.dto.StockAdjustmentDto;

import lombok.RequiredArgsConstructor;

/*
 *	재고 관련 daoImpl 
 *	Mapper: StockMapper
 */
@Repository
@RequiredArgsConstructor
public class StockDaoImpl implements StockDao {

	private final SqlSession session;
	
	/*
	 *  2-1.
	 *  입고내역 Purchase 리스트 조회
	 *  Parameter: productId (상품 id)
	 *  Result: PurchaseDto 
	 */
	@Override
	public List<PurchaseDto> getPurchaseList(int productId) {
		
		return session.selectList("StockMapper.getPurchaseList", productId);
	}

	/*
	 *  2-2.
	 *  출고내역 + 판매내역 조회
	 *  Parameter: productId (상품 id)
	 *  Result: StockAdjustmentDto
	 *  재고 출고 테이블, 판매 테이블 데이터 합쳐서 select 
	 */
	@Override
	public List<StockAdjustmentDto> getAdjustStockAndSalesList(int productId) {
		
		return session.selectList("StockMapper.getAdjustStockAndSalesList", productId);
	}

	/*
	 *  2-3.
	 *  현재 재고현황 리스트 조회
	 *  Parameter: -
	 *  Result: CurrentStockDto (재고 현황)
	 */
	@Override
	public List<CurrentStockDto> getCurrentStockList() {
		
		return session.selectList("StockMapper.getCurrentStockList");
	}

	/*
	 *  3-1.
	 *  입고시 Purchase 테이블에 추가 (입고등록)
	 *  Parameter: ProductDto (상품 입고 정보)
	 *  Result: int
	 */
	@Override
	public int insertPurchase(PurchaseDto dto) {
		
		return session.insert("StockMapper.insertPurchase", dto);
	}

	/*
	 *  3-2.
	 *  출고시 StockAdjustment table 에 row 추가
	 *  Parameter: StockAdjustmentDto (상품 조정 정보)
	 *  Result: int
	 */
	@Override
	public int insertStockAdjustment(StockAdjustmentDto dto) {
		
		return session.insert("StockMapper.insertStockAdjustment", dto);
	}

}
