package jpashop.jpashop.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import jpashop.jpashop.dto.item.form.ItemAddDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("M")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@PrimaryKeyJoinColumn(
    name = "MOVIE_ID"
)
public class Movie extends Item {

    private String director;
    private String actor;

    @Builder
    protected Movie(String name, Integer price, Integer stockQuantity, String director,
        String actor) {
        super(name, price, stockQuantity);
        this.director = director;
        this.actor = actor;
    }

    public static Movie from(ItemAddDTO itemAddDTO) {
        return Movie.builder()
            .director(itemAddDTO.getDirector())
            .actor(itemAddDTO.getActor())
            .name(itemAddDTO.getName())
            .price(itemAddDTO.getPrice())
            .stockQuantity(itemAddDTO.getQuantity())
            .build();
    }
}
