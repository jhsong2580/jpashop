package jpashop.jpashop.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import jpashop.jpashop.dto.order.DeliveryEditDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderTest {

    private Order order;
    private Address address;

    @BeforeEach()
    public void init() {
        order = new Order();
    }

    @Test
    void cancleWithCompleteDelivery() {
        //given
        Delivery delivery = new Delivery(address, DeliveryStatus.COMP);
        order = new Order(OrderStatus.ORDER, null, delivery);

        //when & then
        Assertions.assertThatThrownBy(() -> order.cancle())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("배송중이거나 완료된 주문은 취소할수 없습니다");
    }

    @Test
    void cancleWithProcessingDelivery() {
        //given
        Delivery delivery = new Delivery(address, DeliveryStatus.PROCESSING);
        order = new Order(OrderStatus.ORDER, null, delivery);

        //when & then
        Assertions.assertThatThrownBy(() -> order.cancle())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("배송중이거나 완료된 주문은 취소할수 없습니다");
    }

    @Test
    void cancleWithReadyDelivery() {
        //given
        Delivery delivery = new Delivery(address, DeliveryStatus.READY);
        order = new Order(OrderStatus.ORDER, null, delivery);

        //when
        order.cancle();

        //then
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.CANCEL);
    }

    @Test
    public void editDelivery() {
        //given
        Delivery delivery = new Delivery(address, DeliveryStatus.READY);
        order = new Order(OrderStatus.ORDER, null, delivery);
        DeliveryEditDTO deliveryEditDTO = new DeliveryEditDTO("modifyCity", "modifyStreet",
            "modifyZipcode",
            DeliveryStatus.PROCESSING.name());

        //when
        order.updateDelivery(deliveryEditDTO);
        //then
        assertAll(
            () -> assertThat(order.getDelivery().getDeliveryStatus()).isEqualTo(
                DeliveryStatus.PROCESSING),
            () -> assertThat(order.getDelivery().getAddress()).extracting("city")
                .isEqualTo(deliveryEditDTO.getCity()),
            () -> assertThat(order.getDelivery().getAddress()).extracting("street")
                .isEqualTo(deliveryEditDTO.getStreet()),
            () -> assertThat(order.getDelivery().getAddress()).extracting("zipCode")
                .isEqualTo(deliveryEditDTO.getZipCode())
        );
    }

    @Test
    public void editDeliveryWithCancledOrder() {
        //given
        Delivery delivery = new Delivery(address, DeliveryStatus.READY);
        order = new Order(OrderStatus.CANCEL, null, delivery);
        DeliveryEditDTO deliveryEditDTO = new DeliveryEditDTO("modifyCity", "modifyStreet",
            "modifyZipcode",
            DeliveryStatus.PROCESSING.name());

        //when && then
        assertThatThrownBy(() -> order.updateDelivery(deliveryEditDTO))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("취소된 주문에 대해 배달수정은 불가능합니다");
    }
}