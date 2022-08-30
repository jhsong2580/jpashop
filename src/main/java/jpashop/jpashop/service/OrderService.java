package jpashop.jpashop.service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import jpashop.jpashop.domain.Item;
import jpashop.jpashop.domain.Member;
import jpashop.jpashop.domain.Order;
import jpashop.jpashop.domain.OrderItem;
import jpashop.jpashop.domain.OrderStatus;
import jpashop.jpashop.dto.order.OrderAddDTO;
import jpashop.jpashop.dto.order.OrderAddDTOList;
import jpashop.jpashop.dto.order.OrderDTO;
import jpashop.jpashop.dto.order.OrderEditDTO;
import jpashop.jpashop.repository.ItemRepository;
import jpashop.jpashop.repository.MemberRepository;
import jpashop.jpashop.repository.OrderItemRepository;
import jpashop.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;

    public List<OrderDTO> getList() {
        return orderRepository.findAll()
            .stream()
            .map(OrderDTO::from)
            .collect(Collectors.toList());
    }

    @Transactional
    public OrderDTO saveOrder(OrderAddDTOList orderAddDTOList, Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을수 없습니다"));
        Order order = Order.from(member);
        List<OrderItem> orderItems = new LinkedList<>();

        for (OrderAddDTO orderAddDTO : orderAddDTOList.getOrders()) {
            Item item = itemRepository.findById(orderAddDTO.getItemId())
                .orElseThrow(() -> new IllegalArgumentException("아이템을 찾을수 없습니다"));
            item.minusStockQuantity(orderAddDTO.getCount());
            orderItems.add(OrderItem.of(order, item, orderAddDTO));
        }

        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);

        return OrderDTO.from(order);
    }

    @Transactional
    public OrderDTO changeOrderStatus(Long orderId, OrderEditDTO orderEditDTO) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("주문을 찾을수 없습니다"));

        if (orderEditDTO.getOrderStatus() == OrderStatus.CANCEL) {
            order.cancle();
        }
        return OrderDTO.from(order);
    }
}
