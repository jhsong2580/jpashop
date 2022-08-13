package jpashop.jpashop.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ItemCategoryId implements Serializable {

    private Long item;
    private Long category;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemCategoryId)) {
            return false;
        }
        ItemCategoryId that = (ItemCategoryId) o;
        return Objects.equals(item, that.item) && Objects.equals(category,
            that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, category);
    }
}
