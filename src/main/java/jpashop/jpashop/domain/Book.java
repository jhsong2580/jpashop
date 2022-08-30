package jpashop.jpashop.domain;

import static org.springframework.util.ObjectUtils.isEmpty;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import jpashop.jpashop.dto.item.form.ItemAddDTO;
import jpashop.jpashop.dto.item.form.ItemEditDTO;
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
        if (isEmpty(itemAddDTO.getAuthor()) || isEmpty(itemAddDTO.getIsbn())) {
            throw new IllegalArgumentException("책은 작가와 ISBN정보가 있어야합니다");
        }

        return Book.builder()
            .author(itemAddDTO.getAuthor())
            .isbn(itemAddDTO.getIsbn())
            .name(itemAddDTO.getName())
            .price(itemAddDTO.getPrice())
            .stockQuantity(itemAddDTO.getQuantity())
            .build();
    }

    public void editItem(ItemEditDTO itemEditDTO) {
        if (isEmpty(itemEditDTO.getAuthor()) || isEmpty(itemEditDTO.getIsbn())) {
            throw new IllegalArgumentException("책은 작가와 ISBN정보가 있어야합니다");
        }
        super.editItem(itemEditDTO);
        this.author = itemEditDTO.getAuthor();
        this.isbn = itemEditDTO.getIsbn();
    }
}
