package jpashop.jpashop.dto.order.form;

import java.util.List;
import javax.validation.Valid;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderAddDTOList {

    @Valid
    private List<OrderAddDTO> orders;
}
