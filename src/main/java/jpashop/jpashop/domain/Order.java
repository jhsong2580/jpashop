package jpashop.jpashop.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import jpashop.jpashop.dto.order.DeliveryEditDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "ORDERS")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@TableGenerator(
    name = "ORDER_SEQ",
    table = "ORDER_SEQ_GEN",
    initialValue = 1,
    allocationSize = 50
)
@AttributeOverrides({
    @AttributeOverride(name = "createDate", column = @Column(name = "ORDER_DATE")),
    @AttributeOverride(name = "updateDate", column = @Column(name = "ORDER_UPDATE_DATE"))
})

public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ORDER_SEQ_GEN")
    @Column(name = "ORDER_ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", foreignKey = @ForeignKey(name = "ORDER_MEMBER_FK"))
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "DELIVERY_ID", foreignKey = @ForeignKey(name = "ORDER_DELIVERY_FK"))
    private Delivery delivery;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderItem> orderItemList = new ArrayList<>();

    public Order(OrderStatus orderStatus, Member member, Delivery delivery) {
        this.orderStatus = orderStatus;
        this.member = member;
        this.delivery = delivery;
    }

    public static Order from(Member member) {
        Delivery delivery = new Delivery(member.getAddress(), DeliveryStatus.READY);
        return new Order(OrderStatus.ORDER, member, delivery);
    }

    public void cancle(){
        if(getDelivery().getDeliveryStatus() != DeliveryStatus.READY){
            throw new IllegalArgumentException("배송중이거나 완료된 주문은 취소할수 없습니다");
        }
        orderStatus = OrderStatus.CANCEL;
    }

    public void updateDelivery(DeliveryEditDTO deliveryEditDTO){
        if (this.orderStatus == OrderStatus.CANCEL) {
            throw new IllegalArgumentException("취소된 주문에 대해 배달수정은 불가능합니다");
        }
        if(deliveryEditDTO.getDeliveryStatus() == DeliveryStatus.COMP){
            completeDelivery();
        }
        if(deliveryEditDTO.getDeliveryStatus() == DeliveryStatus.PROCESSING){
            startDelivery();
        }
        if(deliveryEditDTO.getDeliveryStatus() == DeliveryStatus.READY){
            readyDelivery();
        }

        this.delivery.changeAddress(Address.builder()
            .zipCode(deliveryEditDTO.getZipCode())
            .city(deliveryEditDTO.getCity())
            .street(deliveryEditDTO.getStreet())
            .build()
        );
    }

    private void startDelivery() {
        this.delivery.startDelivery();
    }

    private void readyDelivery() {
        this.delivery.readyDelivery();
    }

    private void completeDelivery() {
        this.delivery.completeDelivery();
    }
}
