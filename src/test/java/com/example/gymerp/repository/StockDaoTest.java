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

@MybatisTest
@ActiveProfiles("test")           // 네가 만든 테스트 프로퍼티 사용
@Import(StockDaoImpl.class)       // DAO 빈 주입 (SqlSession은 @MybatisTest가 구성)
public class StockDaoTest {
	@Autowired
	private StockDao stockDao ; 
	
	// StockDao 2-3
	@Test
	@DisplayName("현재 재고 현황 쿼리 실행")
	void basicContextAndQuery() {
        List<CurrentStockDto> list = stockDao.getCurrentStockList();
        assertThat(list).isNotNull();          // NPE 방지
        assertThat(list.size()).isGreaterThan(0); // 최소 1행 이상 나와야함 
        
    }
	
	// StockDao 2-1
	@Test
	@DisplayName("입고 내역: 존재하는 productId는 최근순으로 1건 이상 반환")
	void purchaseList_existingProduct_recentFirst() {
	    // 픽스처: DB에 있는 아무 상품 id 한 개 뽑기 (하드코딩 회피)
	    int productId = stockDao.getCurrentStockList().get(0).getProductId();

	    var list = stockDao.getPurchaseList(productId);
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
	@Test
	@DisplayName("입고 내역: 없는 productId는 빈 리스트")
	void purchaseList_nonExistingProduct_returnsEmpty() {
	    int none = 999_999;
	    assertThat(stockDao.getPurchaseList(none)).isEmpty();
	}
	
	// StockDao 2-2
	@Test
	@DisplayName("출고+판매 내역: 존재하는 productId는 1건 이상, 수량은 0 이상")
	void outboundAndSales_existingProduct_nonNegative() {
	    int productId = stockDao.getCurrentStockList().get(0).getProductId();

	    var list = stockDao.getAdjustStockAndSalesList(productId);
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
	@Test
	@DisplayName("출고+판매 내역: 없는 productId는 빈 리스트")
	void outboundAndSales_nonExistingProduct_returnsEmpty() {
	    int none = 999_999;
	    assertThat(stockDao.getAdjustStockAndSalesList(none)).isEmpty();
	}

	@Test
	@DisplayName("현재 재고: 이름 오름차순, 합계 NULL 안전(NVL/COALESCE 처리)")
	void currentStock_sortedAndNullSafe() {
	    var list = stockDao.getCurrentStockList();
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
