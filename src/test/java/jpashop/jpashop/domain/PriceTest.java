package jpashop.jpashop.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PriceTest {

    private Price price;

    @BeforeEach
    public void init() {
        price = new Price(3);
    }

    @Test
    public void 가격변경() {

        //when
        price.changePrice(5);
        //then
        assertThat(price.getPrice()).isEqualTo(5);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    public void 가격는1이상(int futurePrice) {
        assertThatThrownBy(() -> new Price(futurePrice))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("가격은 0 보다 커야합니다");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    public void 재고변경시_재고는0이상(int futurePrice) {
        assertThatThrownBy(() -> price.changePrice(futurePrice))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("가격은 0 보다 커야합니다");
    }
}