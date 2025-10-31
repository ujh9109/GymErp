package com.example.gymerp.service;

// 🔽 AssertJ의 유창한(플루언트) 단정 메서드 사용 (assertThat, thrownBy 등)
import static org.assertj.core.api.Assertions.*;
// 🔽 Mockito의 when/verify 같은 정적 메서드 사용
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
// 🔽 JUnit5에서 Mockito 확장을 활성화해서 @Mock, @InjectMocks가 동작하게 함
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

// 🔽 프로젝트 실제 DTO/DAO import (서비스 시그니처와 일치해야 함)
import com.example.gymerp.dto.ProductDto;
import com.example.gymerp.dto.PurchaseDto;
import com.example.gymerp.dto.StockAdjustRequestDto;
import com.example.gymerp.dto.StockAdjustmentDto;
import com.example.gymerp.repository.ProductDao;
import com.example.gymerp.repository.StockDao;

/**
 * StockServiceImpl 단위 테스트
 *
 *  목적:
 * - DAO는 "가짜(mock)"로 대체하고, 서비스 로직(검증/분기/위임)만 검증한다.
 * - 트랜잭션, DB, MyBatis 등 외부 요소는 전혀 개입 X (순수 단위 테스트)
 *
 *  기술 스택:
 * - JUnit5 (테스트 프레임워크)
 * - Mockito (mock/stub)
 * - AssertJ (가독성 높은 단정)
 *
 *  팁:
 * - given(준비) / when(실행) / then(검증) 순서로 읽기 쉽게 유지
 */
@ExtendWith(MockitoExtension.class) // JUnit5 + Mockito 통합
class StockServiceImplTest {

    // 🔽 DAO 들은 "가짜 객체"로 주입 (행동을 when(...)으로 지정)
    @Mock private StockDao stockDao;
    @Mock private ProductDao productDao;

    // 🔽 테스트 대상 클래스. 위 @Mock 들이 생성자 주입처럼 들어간다.
    @InjectMocks
    private StockServiceImpl stockService;

    // ─────────────────────────────────────────────────────────────
    //  조회 계열 테스트
    // ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("getProductStockList: 현재 재고현황 - DTO 없이도 위임 검증 가능(빈 리스트)")
    void getProductStockList_ok() {
        //  given
        // CurrentStockDto 클래스를 만들지 않아도 됨. 서비스는 리스트만 반환하므로 "빈 리스트"로도 위임 검증 가능!
        when(stockDao.getCurrentStockList()).thenReturn(List.of());

        //  when
        var result = stockService.getProductStockList();

        //  then
        // - 결과가 "빈 리스트"인지 확인 (= DAO의 반환을 그대로 전달하는지)
        assertThat(result).isEmpty();
        // - DAO 메서드가 정확히 1번 호출됐는지 확인
        verify(stockDao, times(1)).getCurrentStockList();
        // - 그 외 불필요한 상호작용이 없었는지 확인 (안전망)
        verifyNoMoreInteractions(stockDao, productDao);
    }

    @Test
    @DisplayName("getProductInboundDetail: productId <= 0 이면 예외")
    void getProductInboundDetail_invalidId() {
        //  when + then
        assertThatThrownBy(() -> stockService.getProductInboundDetail(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("유효하지 않은 productId");

        // DAO는 전혀 호출되면 안 됨
        verifyNoInteractions(stockDao, productDao);
    }

    @Test
    @DisplayName("getProductInboundDetail: 정상 입력이면 DAO 위임")
    void getProductInboundDetail_ok() {
        //  given
        int productId = 7;
        var p = PurchaseDto.builder()
                .productId(productId)
                .codeBId("CREATINE")
                .quantity(5)
                .build();
        when(stockDao.getPurchaseList(productId)).thenReturn(List.of(p));

        // 🍩 when
        var result = stockService.getProductInboundDetail(productId);

        // 🍰 then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getQuantity()).isEqualTo(5);
        verify(stockDao).getPurchaseList(productId);
        verifyNoMoreInteractions(stockDao, productDao);
    }

    @Test
    @DisplayName("getProductOutboundDetail: productId <= 0 이면 예외")
    void getProductOutboundDetail_invalidId() {
        // 🧁 when + then
        assertThatThrownBy(() -> stockService.getProductOutboundDetail(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("유효하지 않은 productId");

        verifyNoInteractions(stockDao, productDao);
    }

    @Test
    @DisplayName("getProductOutboundDetail: 정상 입력이면 DAO 위임")
    void getProductOutboundDetail_ok() {
        // 🧁 given
        int productId = 9;

        // StockAdjustmentDto는 실제 클래스가 있으니 빌더/세터/목 중 하나를 선택할 수 있음.
        // 여기선 필드 몇 개만 확인하면 되므로 "목(mock)"으로 최소 스텁만 한다.
        StockAdjustmentDto a = mock(StockAdjustmentDto.class);
        when(a.getProductId()).thenReturn(productId);
        when(a.getCodeBId()).thenReturn("WHEY");
        when(a.getQuantity()).thenReturn(2);

        when(stockDao.getAdjustStockAndSalesList(productId)).thenReturn(List.of(a));

        // 🍩 when
        var result = stockService.getProductOutboundDetail(productId);

        // 🍰 then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getQuantity()).isEqualTo(2);
        verify(stockDao).getAdjustStockAndSalesList(productId);
        verifyNoMoreInteractions(stockDao, productDao);
    }

    // ─────────────────────────────────────────────────────────────
    //  조정(입고/출고) 계열 테스트
    // ─────────────────────────────────────────────────────────────

    @Nested
    @DisplayName("adjustProduct 동작 검증")
    class AdjustProductTests {

        @Test
        @DisplayName("수량이 0 이하이면 예외")
        void adjust_invalidQuantity() {
            // 🧁 given
            var req = StockAdjustRequestDto.builder()
                    .action("ADD")
                    .quantity(0) // ❌ 유효하지 않음
                    .build();

            // 🍩 when + 🍰 then
            assertThatThrownBy(() -> stockService.adjustProduct(1, req))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("수량은 1 이상");

            // DAO 호출 없어야 함
            verifyNoInteractions(stockDao, productDao);
        }

        @Test
        @DisplayName("상품이 없으면 예외")
        void adjust_productNotFound() {
            // 🧁 given
            int productId = 123;
            when(productDao.getByNum(productId)).thenReturn(null); // 없는 상품

            var req = StockAdjustRequestDto.builder()
                    .action("ADD")
                    .quantity(3)
                    .build();

            // 🍩 when + 🍰 then
            assertThatThrownBy(() -> stockService.adjustProduct(productId, req))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("유효하지 않은 상품");

            // productDao 조회는 1번 있었고, 그 외 호출은 없어야 함
            verify(productDao).getByNum(productId);
            verifyNoMoreInteractions(productDao);
            verifyNoInteractions(stockDao);
        }

        @Test
        @DisplayName("ADD(입고)면 insertPurchase 호출")
        void adjust_add_ok() {
            // 🧁 given
            int productId = 55;

            // 서비스 로직은 productDto에서 사실상 codeBId만 꺼내 씀 → mock이 제일 간단
            ProductDto product = mock(ProductDto.class);
            when(product.getCodeBId()).thenReturn("WHEY");
            when(productDao.getByNum(productId)).thenReturn(product);

            var req = StockAdjustRequestDto.builder()
                    .action("ADD")      // 입고
                    .quantity(10)
                    .notes("초기 입고")
                    .build();

            // 🍩 when
            stockService.adjustProduct(productId, req);

            // 🍰 then
            // - purchase 인자가 우리가 기대한 값인지 argThat으로 정밀 검증
            verify(productDao).getByNum(productId);
            verify(stockDao).insertPurchase(
                    argThat(p -> p.getProductId() == productId
                            && "WHEY".equals(p.getCodeBId())
                            && p.getQuantity() == 10
                            && "초기 입고".equals(p.getNotes()))
            );
            verifyNoMoreInteractions(stockDao, productDao);
        }

        @Test
        @DisplayName("SUBTRACT(출고)면 insertStockAdjustment 호출")
        void adjust_subtract_ok() {
            // 🧁 given
            int productId = 77;

            ProductDto product = mock(ProductDto.class);
            when(product.getCodeBId()).thenReturn("CREATINE");
            when(productDao.getByNum(productId)).thenReturn(product);

            var req = StockAdjustRequestDto.builder()
                    .action("SUBTRACT") // 출고
                    .quantity(2)
                    .notes("테스트 출고")
                    .build();

            // 🍩 when
            stockService.adjustProduct(productId, req);

            // 🍰 then
            verify(productDao).getByNum(productId);
            verify(stockDao).insertStockAdjustment(
                    argThat(a -> a.getProductId() == productId
                            && "CREATINE".equals(a.getCodeBId())
                            && a.getQuantity() == 2
                            && "테스트 출고".equals(a.getNotes()))
            );
            verifyNoMoreInteractions(stockDao, productDao);
        }

        @Test
        @DisplayName("action 값이 잘못되면 예외")
        void adjust_invalidAction() {
            // 🧁 given
            int productId = 10;

            ProductDto product = mock(ProductDto.class);
            when(product.getCodeBId()).thenReturn("BCAA");
            when(productDao.getByNum(productId)).thenReturn(product);

            var req = StockAdjustRequestDto.builder()
                    .action("WHAT?") // ❌ 유효하지 않은 액션
                    .quantity(1)
                    .build();

            // 🍩 when + 🍰 then
            assertThatThrownBy(() -> stockService.adjustProduct(productId, req))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("올바르지 않은 action");

            verify(productDao).getByNum(productId);
            verifyNoMoreInteractions(productDao);
            verifyNoInteractions(stockDao);
        }
    }
}
