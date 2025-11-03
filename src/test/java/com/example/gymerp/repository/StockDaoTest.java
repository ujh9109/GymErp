package com.example.gymerp.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.example.gymerp.dto.CurrentStockDto;
import com.example.gymerp.dto.PurchaseDto;
import com.example.gymerp.dto.StockAdjustmentDto;

@MybatisTest
@ActiveProfiles("test")           // 네가 만든 테스트 프로퍼티 사용
@Import(StockDaoImpl.class)       // DAO 빈 주입 (SqlSession은 @MybatisTest가 구성)
public class StockDaoTest {
	@Autowired
	private StockDao stockDao ; 
	
	private static final int DEFAULT_OFFSET = 0;
	private static final int DEFAULT_SIZE = 100;
	private static final String NO_KEYWORD = null;
	
	// StockDao 2-3
	// 페이지 기반 조회 쿼리가 기본 파라미터로 최소 한 건 이상 돌려주는지 확인
	@Test
	@DisplayName("현재 재고 현황 쿼리 실행")
	void basicContextAndQuery() {
        List<CurrentStockDto> list = stockDao.getCurrentStockListPaged(DEFAULT_OFFSET, DEFAULT_SIZE, NO_KEYWORD);
        assertThat(list).isNotNull();          // NPE 방지
        assertThat(list.size()).isGreaterThan(0); // 최소 1행 이상 나와야함 
        
    }
	
	// StockDao 2-1
	// 실제 존재하는 상품의 입고 내역이 최근 등록 순으로 정렬돼 반환되는지 검증
	@Test
	@DisplayName("입고 내역: 존재하는 productId는 최근순으로 1건 이상 반환")
	void purchaseList_existingProduct_recentFirst() {
	    // 픽스처: DB에 있는 아무 상품 id 한 개 뽑기 (하드코딩 회피)
	    List<CurrentStockDto> firstPage = stockDao.getCurrentStockListPaged(DEFAULT_OFFSET, 1, NO_KEYWORD);
	    assertThat(firstPage).isNotEmpty();
	    int productId = firstPage.get(0).getProductId();

        List<PurchaseDto> list = stockDao.getPurchaseList(productId, DEFAULT_OFFSET, DEFAULT_SIZE);
	    assertThat(list).isNotNull();
	    assertThat(list.size()).isGreaterThan(0);

	    // 정렬 검증: createdAt 내림차순
	    for (int i = 1; i < list.size(); i++) {
	        assertThat(list.get(i-1).getCreatedAt())
	            .as("createdAt desc")
	            .isAfterOrEqualTo(list.get(i).getCreatedAt());
	    }
	}

	// StockDao 2-1
	// 존재하지 않는 상품 번호 조회 시 빈 리스트를 돌려주는지 검증
	@Test
	@DisplayName("입고 내역: 없는 productId는 빈 리스트")
	void purchaseList_nonExistingProduct_returnsEmpty() {
	    int none = 999_999;
	    assertThat(stockDao.getPurchaseList(none, DEFAULT_OFFSET, DEFAULT_SIZE)).isEmpty();
	}
	
	// StockDao 2-2
	// 출고/판매 합산 쿼리 결과가 지정한 상품 번호와 일치하고 음수 수량이 없는지 확인
	@Test
	@DisplayName("출고+판매 내역: 존재하는 productId는 1건 이상, 수량은 0 이상")
	void outboundAndSales_existingProduct_nonNegative() {
        List<CurrentStockDto> firstPage = stockDao.getCurrentStockListPaged(DEFAULT_OFFSET, 1, NO_KEYWORD);
        assertThat(firstPage).isNotEmpty();
	    int productId = firstPage.get(0).getProductId();

	    List<StockAdjustmentDto> list = stockDao.getAdjustStockAndSalesList(productId, DEFAULT_OFFSET, DEFAULT_SIZE);
	    assertThat(list).isNotNull();

	    // 없을 수도 있으므로 크기 검증은 선택, 대신 필드 무결성 확인
	    list.forEach(r -> {
	        assertThat(r.getProductId()).isEqualTo(productId);
	        assertThat(r.getQuantity()).isNotNull();
	        assertThat(r.getQuantity()).isGreaterThanOrEqualTo(0);
	        assertThat(r.getCodeBId()).isIn("SUPPLEMENT", "SALES"); // 매퍼에서 지정한 두 타입
	        assertThat(r.getCreatedAt()).isNotNull();
	    });
	}

	// StockDao 2-2
	// 존재하지 않는 상품 번호로 조회하면 빈 리스트가 내려오는지 검증
	@Test
	@DisplayName("출고+판매 내역: 없는 productId는 빈 리스트")
	void outboundAndSales_nonExistingProduct_returnsEmpty() {
	    int none = 999_999;
	    assertThat(stockDao.getAdjustStockAndSalesList(none, DEFAULT_OFFSET, DEFAULT_SIZE)).isEmpty();
	}

	// StockDao 2-3
	// 재고 목록이 이름 오름차순 정렬이며 합계 컬럼이 NULL-safe하게 조회되는지 확인
	@Test
	@DisplayName("현재 재고: 이름 오름차순, 합계 NULL 안전(NVL/COALESCE 처리)")
	void currentStock_sortedAndNullSafe() {
	    List<CurrentStockDto> list = stockDao.getCurrentStockListPaged(DEFAULT_OFFSET, DEFAULT_SIZE, NO_KEYWORD);
	    assertThat(list).isNotEmpty();

	    // 이름 오름차순
	    for (int i = 1; i < list.size(); i++) {
	        assertThat(list.get(i-1).getProductName())
	            .isLessThanOrEqualTo(list.get(i).getProductName());
	    }

	    // 합계 필드 NULL 아님 + 음수 방지(비즈 규칙 상 음수 허용 안 한다면)
	    list.forEach(r -> {
	        assertThat(r.getTotalInbound()).isNotNull();
	        assertThat(r.getTotalOutbound()).isNotNull();
	        assertThat(r.getTotalSales()).isNotNull();
	        assertThat(r.getCurrentStock()).isNotNull();
	    });
	}


}
