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
@DiscriminatorValue("B")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@PrimaryKeyJoinColumn(
    name = "BOOK_ID"
)
public class Book extends Item {

    private String author;
    private String isbn;

    @Builder
    protected Book(String name, Integer price, Integer stockQuantity, String author, String isbn) {
        super(name, price, stockQuantity);
        this.author = author;
        this.isbn = isbn;
    }

    public static Book from(ItemAddDTO itemAddDTO) {
        return Book.builder()
            .author(itemAddDTO.getAuthor())
            .isbn(itemAddDTO.getIsbn())
            .name(itemAddDTO.getName())
            .price(itemAddDTO.getPrice())
            .stockQuantity(itemAddDTO.getQuantity())
            .build();
    }
}
