package jpashop.jpashop.dto.order;

import jpashop.jpashop.domain.OrderItem;
import lombok.Getter;

@Getter
public class OrderItemDTO {

    private Long itemId;
    private Long orderId;
    private Integer price;
    private String itemName;
    private Integer itemCount;
    private String orderStatus;
    private String deliveryStatus;

    public OrderItemDTO(Long itemId, Long orderId, Integer price, String itemName,
        Integer itemCount,
        String orderStatus, String deliveryStatus) {
        this.itemId = itemId;
        this.orderId = orderId;
        this.price = price;
        this.itemName = itemName;
        this.itemCount = itemCount;
        this.orderStatus = orderStatus;
        this.deliveryStatus = deliveryStatus;
    }

    public static OrderItemDTO from(OrderItem orderItem) {
        return new OrderItemDTO(
            orderItem.getItem().getId(),
            orderItem.getOrder().getId(),
            orderItem.getOrderPrice(),
            orderItem.getItem().getName(),
            orderItem.getCount(),
            orderItem.getOrder().getOrderStatus().name(),
            orderItem.getOrder().getDelivery().getDeliveryStatus().name()
        );
    }
}
