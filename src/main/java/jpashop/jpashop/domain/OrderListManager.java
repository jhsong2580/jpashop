package jpashop.jpashop.domain;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

@Embeddable
public class OrderListManager {

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new LinkedList<>();
}
