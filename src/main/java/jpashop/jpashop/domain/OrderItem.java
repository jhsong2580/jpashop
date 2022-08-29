package jpashop.jpashop.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import jpashop.jpashop.dto.order.OrderAddDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(OrderItemId.class)
@Getter
public class OrderItem {

    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Order order; //매핑할 외래키 이름 (default : 필드명+_+참조하는테이블컬럼명)  order_order_id

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    private Integer orderPrice;

    private Integer count;

    protected OrderItem(Order order, Item item, Integer orderPrice, Integer count) {
        this.order = order;
        this.item = item;
        this.orderPrice = orderPrice;
        this.count = count;
    }

    public static OrderItem of(Order order, Item item, OrderAddDTO orderAddDTO){
        return new OrderItem(order, item, orderAddDTO.getCount() * item.getPrice(),
            orderAddDTO.getCount());
    }
}
