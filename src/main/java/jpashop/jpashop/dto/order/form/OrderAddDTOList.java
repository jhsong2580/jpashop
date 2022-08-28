package jpashop.jpashop.dto.order.form;

import java.util.LinkedList;
import java.util.List;
import lombok.Data;

@Data
public class OrderAddDTOList {

    private List<Long> itemId = new LinkedList<>();
    private List<Long> count = new LinkedList<>();
}
