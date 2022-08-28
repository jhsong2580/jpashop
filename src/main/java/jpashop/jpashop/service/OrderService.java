package jpashop.jpashop.service;

import java.util.List;
import java.util.stream.Collectors;
import jpashop.jpashop.domain.Item;
import jpashop.jpashop.domain.Member;
import jpashop.jpashop.domain.Order;
import jpashop.jpashop.domain.OrderItem;
import jpashop.jpashop.dto.order.OrderItemDTO;
import jpashop.jpashop.dto.order.form.OrderAddDTO;
import jpashop.jpashop.repository.ItemRepository;
import jpashop.jpashop.repository.MemberRepository;
import jpashop.jpashop.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderItemRepository orderItemRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    public List<OrderItemDTO> getList() {
        return orderItemRepository.findAll()
            .stream()
            .map(OrderItemDTO::from)
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
