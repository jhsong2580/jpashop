package jpashop.jpashop.dto.item.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import jpashop.jpashop.domain.Album;
import jpashop.jpashop.domain.Book;
import jpashop.jpashop.domain.Item;
import jpashop.jpashop.domain.ItemType;
import jpashop.jpashop.domain.Movie;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
public class ItemEditDTO {

    @NotNull(message = "아이템 키는 비어있을수 없습니다", groups = {ItemValidationGroups.defaultGroup.class})
    @Min(value = 0)
    private Long itemId;
    @NotEmpty(message = "아이템 이름은 비어있을수 없습니다.", groups = {ItemValidationGroups.defaultGroup.class})
    private String name;
    @NotNull(message = "가격은 필수입니다.", groups = {ItemValidationGroups.defaultGroup.class})
    @Min(value = 1, message = "가격은 1원 이상이여야 합니다", groups = {
        ItemValidationGroups.defaultGroup.class})
    private Integer price;
    @NotNull(message = "수량은 필수입니다.", groups = {ItemValidationGroups.defaultGroup.class})
    @Min(value = 1, message = "아이템 수량은 1원 이상이여야 합니다.", groups = {
        ItemValidationGroups.defaultGroup.class})
    private Integer quantity;

    private String dType;

    //Book
    @NotEmpty(message = "작가는 필수입니다")
    private String author;
    @NotEmpty(message = "isbn은 필수입니다")
    private String isbn;

    //Album
    @NotEmpty(message = "작가는 필수입니다")
    private String artist;
    private String etc;

    //Movie
    @NotEmpty(message = "기획자는 필수입니다.")
    private String director;
    @NotEmpty(message = "배우는 필수입니다")
    private String actor;

    public static ItemEditDTO from(Item item) {
        if (item instanceof Movie) {
            return from((Movie) item);
        }
        if (item instanceof Album) {
            return from((Album) item);
        }
        if (item instanceof Book) {
            return from((Book) item);
        }
        return ItemEditDTO.builder()
            .itemId(item.getId())
            .name(item.getName())
            .price(item.getPrice())
            .quantity(item.getStockQuantity())
            .build();
    }

    private static ItemEditDTO from(Movie movie) {
        return ItemEditDTO.builder()
            .itemId(movie.getId())
            .name(movie.getName())
            .price(movie.getPrice())
            .quantity(movie.getStockQuantity())
            .dType(ItemType.MOVIE.getWebPageDype())
            .director(movie.getDirector())
            .actor(movie.getActor())
            .build();
    }

    private static ItemEditDTO from(Album album) {
        return ItemEditDTO.builder()
            .itemId(album.getId())
            .name(album.getName())
            .price(album.getPrice())
            .quantity(album.getStockQuantity())
            .dType(ItemType.ALBUM.getWebPageDype())
            .artist(album.getArtist())
            .etc(album.getEtc())
            .build();
    }

    private static ItemEditDTO from(Book book) {
        return ItemEditDTO.builder()
            .itemId(book.getId())
            .name(book.getName())
            .price(book.getPrice())
            .quantity(book.getStockQuantity())
            .dType(ItemType.BOOK.getWebPageDype())
            .author(book.getAuthor())
            .isbn(book.getIsbn())
            .build();
    }
}
