package jpashop.jpashop.domain;

import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;

@Getter
public class OrderItemId implements Serializable {

    private Long order;
    private Long item;

    //리플렉션을 위해 기본생성자가 필요하며, 영속성 컨텍스트의 키로 사용하기 위해 동등성 비교를 가능하게 해줘야한다.

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderItemId)) {
            return false;
        }
        OrderItemId that = (OrderItemId) o;
        return Objects.equals(order, that.order) && Objects.equals(item, that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, item);
    }
}
