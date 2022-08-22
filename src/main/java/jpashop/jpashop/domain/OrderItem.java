package jpashop.jpashop.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(OrderItemId.class)
@Getter
public class OrderItem {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order; //매핑할 외래키 이름 (default : 필드명+_+참조하는테이블컬럼명)  order_order_id

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    private Integer orderPrice;

    private Integer count;
}
