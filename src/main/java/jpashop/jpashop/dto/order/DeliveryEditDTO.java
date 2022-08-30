package jpashop.jpashop.dto.order;

import static lombok.AccessLevel.PROTECTED;

import javax.validation.constraints.NotEmpty;
import jpashop.jpashop.domain.DeliveryStatus;
import jpashop.jpashop.validator.order.annotation.CustomEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class DeliveryEditDTO {

    @NotEmpty
    private String city;
    @NotEmpty
    private String street;
    @NotEmpty
    private String zipCode;
    @CustomEnum(enumClass = DeliveryStatus.class)
    private String deliveryStatus;

    @Builder
    public DeliveryEditDTO(String city, String street, String zipCode, String deliveryStatus) {
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
        this.deliveryStatus = deliveryStatus;
    }

    public DeliveryStatus getDeliveryStatus() {
        return DeliveryStatus.valueOf(deliveryStatus);
    }
}
