package jpashop.jpashop.service;

import java.util.List;
import java.util.stream.Collectors;
import jpashop.jpashop.dto.order.OrderItemDTO;
import jpashop.jpashop.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderItemRepository orderItemRepository;

    public List<OrderItemDTO> getList() {
        return orderItemRepository.findAll()
            .stream()
            .map(OrderItemDTO::from)
            .collect(Collectors.toList());
    }
}
