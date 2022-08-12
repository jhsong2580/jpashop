package jpashop.jpashop.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import lombok.AccessLevel;
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
}
