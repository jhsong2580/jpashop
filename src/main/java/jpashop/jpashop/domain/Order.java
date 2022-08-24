package jpashop.jpashop.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
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
import javax.persistence.Table;
import javax.persistence.TableGenerator;
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
}
