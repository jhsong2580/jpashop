package jpashop.jpashop.dto.item.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ItemAddDTO {

    @NotEmpty(message = "아이템 이름은 비어있을수 없습니다.", groups = {ItemValidationGroups.defaultGroup.class})
    private String name;
    @NotNull(message = "가격은 필수입니다.", groups = {ItemValidationGroups.defaultGroup.class})
    @Min(value = 1, message = "가격은 1원 이상이여야 합니다", groups = {ItemValidationGroups.defaultGroup.class})
    private Integer price;
    @NotNull(message = "수량은 필수입니다.", groups = {ItemValidationGroups.defaultGroup.class})
    @Min(value = 1, message = "아이템 수량은 1원 이상이여야 합니다.", groups = {ItemValidationGroups.defaultGroup.class})
    private Integer quantity;

    private String dtype;

    //Book
    @NotEmpty(message = "작가는 필수입니다", groups = ItemValidationGroups.bookGroup.class)
    private String author;
    @NotEmpty(message = "isbn은 필수입니다", groups = ItemValidationGroups.bookGroup.class)
    private String isbn;

    //Album
    @NotEmpty(message = "작가는 필수입니다", groups = ItemValidationGroups.albumGroup.class)
    private String artist;
    private String etc;

    //Movie
    @NotEmpty(message = "기획자는 필수입니다.", groups = ItemValidationGroups.movieGroup.class)
    private String director;
    @NotEmpty(message = "배우는 필수입니다", groups = ItemValidationGroups.movieGroup.class)
    private String actor;
}
