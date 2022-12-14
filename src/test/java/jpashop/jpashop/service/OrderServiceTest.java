package jpashop.jpashop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
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
import jpashop.jpashop.dto.order.DeliveryEditDTO;
import jpashop.jpashop.dto.order.OrderAddDTO;
import jpashop.jpashop.dto.order.OrderAddDTOList;
import jpashop.jpashop.dto.order.OrderDTO;
import jpashop.jpashop.dto.order.OrderEditDTO;
import jpashop.jpashop.repository.ItemRepository;
import jpashop.jpashop.repository.MemberRepository;
import jpashop.jpashop.repository.OrderItemRepository;
import jpashop.jpashop.repository.OrderRepository;
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
        order = new Order(OrderStatus.ORDER, member, new Delivery(member.getAddress(), DeliveryStatus.READY));
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
            .hasMessage("?????????????????? ????????? ????????? ???????????? ????????????");
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
        assertAll(
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
        doThrow(new IllegalArgumentException("????????? 0?????? ???????????????")).when(item)
            .minusStockQuantity(anyInt());
        when(memberRepository.findById(1L)).thenReturn(
            Optional.of(member));

        //when & then
        assertThatThrownBy(() -> orderService.saveOrder(orderAddDTOList, 1L))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("????????? 0?????? ???????????????");
    }


    @Test
    public void editDelivery() {
        //given
        Address address = member.getAddress();
        Delivery delivery = new Delivery(address, DeliveryStatus.READY);
        DeliveryEditDTO deliveryEditDTO = new DeliveryEditDTO("modifyCity", "modifyStreet",
            "modifyZipcode",
            DeliveryStatus.PROCESSING.name());
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        //when
        OrderDTO orderDTO = orderService.editDelivery(1L, deliveryEditDTO);

        //then
        assertAll(
            () -> assertThat(orderDTO.getDeliveryStatus()).isEqualTo(
                deliveryEditDTO.getDeliveryStatus().name()),
            () -> assertThat(orderDTO.getZipCode()).isEqualTo(deliveryEditDTO.getZipCode()),
            () -> assertThat(orderDTO.getCity()).isEqualTo(deliveryEditDTO.getCity()),
            () -> assertThat(orderDTO.getStreet()).isEqualTo(deliveryEditDTO.getStreet())
        );
    }
}