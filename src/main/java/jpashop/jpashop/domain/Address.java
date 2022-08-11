package jpashop.jpashop.domain;

import java.util.Objects;
import javax.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipCode;

    //값타입이기 때문에 동등성 정의

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        Address address = (Address) o;
        return Objects.equals(city, address.city) && Objects.equals(street,
            address.street) && Objects.equals(zipCode, address.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, street, zipCode);
    }
}
