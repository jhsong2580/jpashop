package jpashop.jpashop.repository;

import jpashop.jpashop.domain.OrderItem;
import jpashop.jpashop.domain.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {

}
