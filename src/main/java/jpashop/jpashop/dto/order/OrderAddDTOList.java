package jpashop.jpashop.dto.order;

import java.util.LinkedList;
import java.util.List;
import javax.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class OrderAddDTOList {

    @Valid
    private List<OrderAddDTO> orders = new LinkedList<>();
}
