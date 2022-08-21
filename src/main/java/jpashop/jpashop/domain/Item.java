package jpashop.jpashop.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Embedded;
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
    @Embedded
    private Price price;
    @Embedded
    private StockQuantity stockQuantity;
    @OneToMany(mappedBy = "item")
    private List<ItemCategory> itemCategories = new ArrayList<>();

    public Item(String name, Integer price, Integer stockQuantity) {
        this.name = name;
        this.price = new Price(price);
        this.stockQuantity = new StockQuantity(stockQuantity);
    }


    public void editItem(ItemEditDTO itemEditDTO) {
        this.name = itemEditDTO.getName();
        this.price.changePrice(itemEditDTO.getPrice());
        this.stockQuantity.changeStockQuantity(itemEditDTO.getQuantity());
    }

    public Integer getPrice(){
        return price.getPrice();
    }

    public Integer getStockQuantity(){
        return stockQuantity.getStockQuantity();
    }
}
