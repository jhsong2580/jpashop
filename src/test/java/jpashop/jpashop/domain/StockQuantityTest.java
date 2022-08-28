package jpashop.jpashop.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class StockQuantityTest {

    private StockQuantity stockQuantity;

    @BeforeEach
    public void init() {
        stockQuantity = new StockQuantity(3);
    }

    @Test
    public void 재고변경() {

        //when
        stockQuantity.changeStockQuantity(5);
        //then
        assertThat(stockQuantity.getStockQuantity()).isEqualTo(5);
    }

    @ParameterizedTest
    @ValueSource(ints = {-999, -1})
    public void 재고는0이상(int futureQuantity) {
        assertThatThrownBy(() -> new StockQuantity(futureQuantity))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("수량은 0보다 커야합니다");
    }

    @ParameterizedTest
    @ValueSource(ints = {-999, -1})
    public void 재고변경시_재고는0이상(int futureQuantity) {
        assertThatThrownBy(() -> stockQuantity.changeStockQuantity(futureQuantity))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("수량은 0보다 커야합니다");
    }
}