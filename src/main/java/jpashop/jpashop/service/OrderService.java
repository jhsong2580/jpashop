package jpashop.jpashop.service;

import java.util.List;
import java.util.stream.Collectors;
import jpashop.jpashop.dto.order.OrderDTO;
import jpashop.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public List<OrderDTO> getList() {
        return orderRepository.findAll()
            .stream()
            .map(OrderDTO::from)
            .collect(Collectors.toList());
    }

    @Transactional
    public void saveOrder(OrderAddDTO orderAddDTO, Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을수 없습니다"));
        Item item = itemRepository.findById(orderAddDTO.getItemId())
            .orElseThrow(() -> new IllegalArgumentException("아이템을 찾을수 없습니다"));

        item.minusStockQuantity(orderAddDTO.getCount());

        Order order = Order.from(member);
        OrderItem orderItem = OrderItem.of(order, item, orderAddDTO);
        orderItemRepository.save(orderItem);
    }
}
