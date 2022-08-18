package jpashop.jpashop.dto.item;

import jpashop.jpashop.domain.Item;
import lombok.Getter;

@Getter
public class ItemDTO {

    private Long itemId;
    private String name;
    private Integer price;
    private Integer stock;

    public ItemDTO(Long itemId, String name, Integer price, Integer stock) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public static ItemDTO from(Item item){
        return new ItemDTO(
            item.getId(),
            item.getName(),
            item.getPrice(),
            item.getStockQuantity()
        );
    }
}
