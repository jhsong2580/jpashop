package jpashop.jpashop.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemCategory {

    @EmbeddedId
    private ItemCategoryId itemCategoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("item")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("category")
    private Category category;
}
