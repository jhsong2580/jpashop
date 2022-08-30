package jpashop.jpashop.domain;

import static org.assertj.core.api.Assertions.assertThat;

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
}