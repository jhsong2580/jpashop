package jpashop.jpashop.dto.order;

import jpashop.jpashop.domain.Order;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderDTO {

    private Long id;
    private String orderStatus;
    private Long memberId;
    private String memberName;
    private String city;
    private String street;
    private String zipCode;
    private String deliveryStatus;

    @Builder
    public OrderDTO(Long id, String orderStatus, Long memberId, String memberName, String city,
        String street, String zipCode, String deliveryStatus) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.memberId = memberId;
        this.memberName = memberName;
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
        this.deliveryStatus = deliveryStatus;
    }

    public static OrderDTO from(Order order) {
        return OrderDTO.builder()
            .id(order.getId())
            .orderStatus(order.getOrderStatus().name())
            .memberId(order.getMember().getId())
            .memberName(order.getMember().getName())
            .city(order.getDelivery().getAddress().getCity())
            .street(order.getDelivery().getAddress().getStreet())
            .zipCode(order.getDelivery().getAddress().getZipCode())
            .deliveryStatus(order.getDelivery().getDeliveryStatus().name())
            .build();
    }
}
