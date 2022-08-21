package jpashop.jpashop.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import jpashop.jpashop.dto.item.form.ItemEditDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    private Long id;
    private String name;
    private Integer price;
    private Integer stockQuantity;
    @OneToMany(mappedBy = "item")
    private List<ItemCategory> itemCategories = new ArrayList<>();

    public Item(String name, Integer price, Integer stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }


    public void editItem(ItemEditDTO itemEditDTO) {
        if (itemEditDTO.getPrice() <= 0) {
            throw new IllegalArgumentException("가격은 0 보다 커야합니다");
        }
        if (itemEditDTO.getQuantity() <= 0) {
            throw new IllegalArgumentException("수량은 0보다 커야합니다");
        }

        this.name = itemEditDTO.getName();
        this.price = itemEditDTO.getPrice();
        this.stockQuantity = itemEditDTO.getQuantity();
    }
}
