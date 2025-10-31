package com.example.gymerp.scenario;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.example.gymerp.dto.StockAdjustRequestDto;
import com.example.gymerp.repository.StockDao;
import com.example.gymerp.service.StockService;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class StockServiceIntegrationTest {

    @Autowired
    private StockService stockService;
    
    @Autowired
    private StockDao stockDao;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @BeforeEach
    void setupProduct() {
        // ✅ DB-중립적으로 테이블 존재 확인 (H2/Oracle 모두 통과)
        List<String> tables;
        try {
            tables = jdbcTemplate.queryForList(
                "SELECT TABLE_NAME FROM USER_TABLES WHERE TABLE_NAME IN ('SALESITEM','SALES_ITEM')",
                String.class
            );
        } catch (Exception e) {
            tables = jdbcTemplate.queryForList(
                "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES " +
                "WHERE TABLE_SCHEMA = SCHEMA() AND TABLE_NAME IN ('SALESITEM','SALES_ITEM')",
                String.class
            );
        }
        tables.forEach(t -> System.out.println("📦 TABLE: " + t));

        // (선택) H2 버전 확인
        try {
            String v = jdbcTemplate.queryForObject("SELECT H2VERSION() FROM DUAL", String.class);
            System.out.println("H2 VERSION = " + v);
        } catch (Exception ignore) {
            System.out.println("Not H2 (H2VERSION() unavailable).");
        }

        // CODEA upsert
        Integer codeACount = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM CODEA WHERE CODEAID = 'PRODUCT_TYPE'",
            Integer.class
        );
        if (codeACount == 0) {
            jdbcTemplate.update("""
                INSERT INTO CODEA (CODEAID, NAME)
                VALUES ('PRODUCT_TYPE', '상품유형')
            """);
        }

        // CODEB upsert
        Integer codeBCount = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM CODEB WHERE CODEBID = 'SUPPLEMENT'",
            Integer.class
        );
        if (codeBCount == 0) {
            jdbcTemplate.update("""
                INSERT INTO CODEB (CODEBID, CODEAID, NAME)
                VALUES ('SUPPLEMENT', 'PRODUCT_TYPE', '보충제')
            """);
        }

        // PRODUCT upsert
        Integer productCount = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM PRODUCT WHERE PRODUCTID = 1",
            Integer.class
        );
        if (productCount == 0) {
            jdbcTemplate.update("""
                INSERT INTO PRODUCT (PRODUCTID, NAME, CODEBID, PRICE, CREATEDAT)
                VALUES (1, '테스트상품', 'SUPPLEMENT', 10000, SYSTIMESTAMP)
            """);
        }
     // 시퀀스 값을 테이블 현재 최대값 + 1 로 맞춤
    Integer nextAdj = jdbcTemplate.queryForObject(
    	    "SELECT COALESCE(MAX(ADJUSTMENTID),0) + 1 FROM STOCKADJUSTMENT", Integer.class);
    	jdbcTemplate.execute("ALTER SEQUENCE STOCK_ADJUSTMENT_SEQ RESTART WITH " + nextAdj);

    	Integer nextPur = jdbcTemplate.queryForObject(
    	    "SELECT COALESCE(MAX(PURCHASEID),0) + 1 FROM PURCHASE", Integer.class);
    	jdbcTemplate.execute("ALTER SEQUENCE PURCHASE_SEQ RESTART WITH " + nextPur);

    	Integer nextSi = jdbcTemplate.queryForObject(
    	    "SELECT COALESCE(MAX(ITEMSALESID),0) + 1 FROM SALESITEM", Integer.class);
    	jdbcTemplate.execute("ALTER SEQUENCE SALESITEM_SEQ RESTART WITH " + nextSi);
    }


    @Test
    @DisplayName("✅ ADD 요청 시 Purchase 테이블에 insert 된다")
    @Rollback(true) // 실제 DB에 남겨두고 싶다면 false, 자동 롤백 원하면 true
    void addStock_insertsPurchaseRecord() {
        // given
        int productId = 1; // 테스트용 productId (DB에 존재해야 함)
        var req = StockAdjustRequestDto.builder()
                .action("ADD")
                .quantity(3)
                .notes("테스트 입고")
                .build();

        // when
        stockService.adjustProduct(productId, req);

        // then
        var purchaseList = stockDao.getPurchaseList(productId);
        assertThat(purchaseList).isNotEmpty();
        System.out.println("✅ Purchase insert 성공! size=" + purchaseList.size());
        
        
    }

    @Test
    @DisplayName("✅ SUBTRACT 요청 시 StockAdjustment 테이블에 insert 된다")
    @Rollback(false)
    void subtractStock_insertsAdjustmentRecord() {
        int productId = 1;
        var req = StockAdjustRequestDto.builder()
                .action("SUBTRACT")
                .quantity(2)
                .notes("테스트 차감")
                .build();

        stockService.adjustProduct(productId, req);

        var adjList = stockDao.getAdjustStockAndSalesList(productId);
        assertThat(adjList).isNotEmpty();
        System.out.println("✅ StockAdjustment insert 성공! size=" + adjList.size());
    }
}
