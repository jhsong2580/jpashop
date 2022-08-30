package jpashop.jpashop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;
import jpashop.jpashop.domain.Address;
import jpashop.jpashop.domain.Book;
import jpashop.jpashop.domain.Delivery;
import jpashop.jpashop.domain.DeliveryStatus;
import jpashop.jpashop.domain.Member;
import jpashop.jpashop.domain.Order;
import jpashop.jpashop.domain.OrderStatus;
import jpashop.jpashop.dto.order.OrderAddDTO;
import jpashop.jpashop.dto.order.OrderAddDTOList;
import jpashop.jpashop.dto.order.OrderDTO;
import jpashop.jpashop.dto.order.OrderEditDTO;
import jpashop.jpashop.repository.ItemRepository;
import jpashop.jpashop.repository.MemberRepository;
import jpashop.jpashop.repository.OrderItemRepository;
import jpashop.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    private OrderService orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private OrderAddDTOList orderAddDTOList;
    @Mock
    private Book item;

    private Order order;
    private Member member;


    @BeforeEach
    public void init() {
        orderService = new OrderService(orderRepository, memberRepository, itemRepository,
            orderItemRepository
        );
        Address address = new Address("city", "street", "zipCode");
        member = new Member("name", "password", address);
        Delivery delivery = new Delivery(address, DeliveryStatus.READY);
        order = new Order(OrderStatus.ORDER, member, delivery);
    }

    @Test
    public void changeOrderStatusNormalTest() {
        //given
        OrderEditDTO orderEditDTO = new OrderEditDTO(OrderStatus.CANCEL.name());
        order = new Order(OrderStatus.ORDER, null, new Delivery(null, DeliveryStatus.READY));
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        //when
        orderService.changeOrderStatus(1L, orderEditDTO);

        //then
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.CANCEL);
    }

    @Test
    public void changeOrderStatusAbnormalTest() {
        //given
        OrderEditDTO orderEditDTO = new OrderEditDTO(OrderStatus.CANCEL.name());
        order = new Order(OrderStatus.ORDER, null, new Delivery(null, DeliveryStatus.COMP));
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        //when & then
        assertThatThrownBy(() -> orderService.changeOrderStatus(1L, orderEditDTO))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("배송중이거나 완료된 주문은 취소할수 없습니다");
    }

    @Test
    public void orderSaveNormalTest() {
        //given
        OrderAddDTO order1 = new OrderAddDTO(1L, 2);
        OrderAddDTO order2 = new OrderAddDTO(2L, 3);
        when(orderAddDTOList.getOrders()).thenReturn(Arrays.asList(order1, order2));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(itemRepository.findById(2L)).thenReturn(Optional.of(item));
        when(memberRepository.findById(1L)).thenReturn(
            Optional.of(member));

        //when
        OrderDTO orderDTO = orderService.saveOrder(orderAddDTOList, 1L);

        //then
        Assertions.assertAll(
            () -> assertThat(orderDTO.getOrderStatus()).isEqualTo(OrderStatus.ORDER.name()),
            () -> assertThat(orderDTO.getDeliveryStatus()).isEqualTo(DeliveryStatus.READY.name())
        );
    }

    @Test
    public void orderSaveAbnormalTestLackStock() {
        //given
        OrderAddDTO order1 = new OrderAddDTO(1L, 2);
        OrderAddDTO order2 = new OrderAddDTO(2L, 3);
        when(orderAddDTOList.getOrders()).thenReturn(Arrays.asList(order1, order2));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        doThrow(new IllegalArgumentException("수량은 0보다 커야합니다")).when(item)
            .minusStockQuantity(anyInt());
        when(memberRepository.findById(1L)).thenReturn(
            Optional.of(member));

        //when & then
        assertThatThrownBy(() -> orderService.saveOrder(orderAddDTOList, 1L))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("수량은 0보다 커야합니다");
    }
}