package jpashop.jpashop.domain;

import java.util.Objects;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Price {

    private Integer price;

    public Price(Integer price) {
        validate(price);
        this.price = price;
    }

    public void changePrice(Integer price){
        validate(price);
        this.price = price;
    }

    private void validate(Integer price) {
        if (price <= 0) {
            throw new IllegalArgumentException("가격은 0 보다 커야합니다");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Price)) {
            return false;
        }
        Price price1 = (Price) o;
        return Objects.equals(price, price1.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
