package jpashop.jpashop.dto.order.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class OrderAddDTO {

    @NotEmpty
    private String itemId;
    @Min(1)
    private int count;

    public OrderAddDTO(String itemId, int count) {
        this.itemId = itemId;
        this.count = count;
    }
}
