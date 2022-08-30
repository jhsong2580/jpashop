package jpashop.jpashop.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SequenceGenerator(name = "DELIVERY_SEQ_GEN", //sequence generator 이름
    sequenceName = "DELIVERY_SEQ", //sequence 이름
    initialValue = 10, // 시작값
    allocationSize = 1) //메모리에 한번에 로딩할 sequence 갯수
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DELIVERY_SEQ_GEN")
    @Column(name = "DELIVERY_ID")
    private Long id;

    @Embedded
    private Address address = new Address();

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private DeliveryStatus deliveryStatus;

    public Delivery(Address address, DeliveryStatus deliveryStatus) {
        this.address = address;
        this.deliveryStatus = deliveryStatus;
    }

    public void changeAddress(Address address){
        this.address = address;
    }

    public void completeDelivery(){
        this.deliveryStatus = DeliveryStatus.COMP;
    }

    public void readyDelivery(){
        this.deliveryStatus = DeliveryStatus.READY;
    }

    public void startDelivery(){
        this.deliveryStatus = DeliveryStatus.PROCESSING;
    }
}
