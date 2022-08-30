package jpashop.jpashop.dto.order;

import jpashop.jpashop.domain.OrderStatus;
import jpashop.jpashop.validator.order.annotation.CustomEnum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderEditDTO {

    @CustomEnum(enumClass = OrderStatus.class)
    private String orderStatus;

    public OrderEditDTO(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public OrderStatus getOrderStatus() {
        return OrderStatus.valueOf(orderStatus);
    }
}
