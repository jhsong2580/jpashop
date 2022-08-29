package jpashop.jpashop.dto.order;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class OrderAddDTO {

    @NotNull(message = "주문할 아이템은 필수입니다")
    private Long itemId;
    @Min(value = 1L, message = "주문할 갯수는 1 이상이여야 합니다")
    private Integer count;

}
