package jpashop.jpashop.dto.item;

import jpashop.jpashop.domain.Album;
import jpashop.jpashop.domain.Book;
import jpashop.jpashop.domain.Item;
import jpashop.jpashop.domain.ItemType;
import jpashop.jpashop.domain.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ItemDTO {

    private Long itemId;
    private String name;
    private Integer price;
    private Integer stock;
    private String dType;
    //Book
    private String author;
    private String isbn;

    //Album
    private String artist;
    private String etc;

    //Movie
    private String director;
    private String actor;

    public static ItemDTO from(Item item) {
        if(item instanceof Movie){
            return from((Movie)item);
        }
        if(item instanceof Album){
            return from((Album)item);
        }
        if(item instanceof Book){
            return from((Book)item);
        }
        return ItemDTO.builder()
            .itemId(item.getId())
            .name(item.getName())
            .price(item.getPrice())
            .stock(item.getStockQuantity())
            .build();
    }

    private static ItemDTO from(Movie movie) {
        return ItemDTO.builder()
            .itemId(movie.getId())
            .name(movie.getName())
            .price(movie.getPrice())
            .stock(movie.getStockQuantity())
            .dType(ItemType.MOVIE.getWebPageDype())
            .director(movie.getDirector())
            .actor(movie.getActor())
            .build();
    }

    private static ItemDTO from(Album album) {
        return ItemDTO.builder()
            .itemId(album.getId())
            .name(album.getName())
            .price(album.getPrice())
            .stock(album.getStockQuantity())
            .dType(ItemType.ALBUM.getWebPageDype())
            .artist(album.getArtist())
            .etc(album.getEtc())
            .build();
    }

    private static ItemDTO from(Book book) {
        return ItemDTO.builder()
            .itemId(book.getId())
            .name(book.getName())
            .price(book.getPrice())
            .stock(book.getStockQuantity())
            .dType(ItemType.BOOK.getWebPageDype())
            .author(book.getAuthor())
            .isbn(book.getIsbn())
            .build();
    }
}
