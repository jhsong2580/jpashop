package jpashop.jpashop.domain;

import java.util.Objects;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StockQuantity {

    private Integer stockQuantity;

    public StockQuantity(Integer stockQuantity) {
        validate(stockQuantity);
        this.stockQuantity = stockQuantity;
    }

    public void changeStockQuantity(Integer stockQuantity) {
        validate(stockQuantity);
        this.stockQuantity = stockQuantity;
    }

    public void minus(int count) {
        validate(stockQuantity - count);
        this.stockQuantity -= count;
    }

    private void validate(Integer stockQuantity) {
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("수량은 0보다 커야합니다");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StockQuantity)) {
            return false;
        }
        StockQuantity that = (StockQuantity) o;
        return Objects.equals(stockQuantity, that.stockQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockQuantity);
    }
}
