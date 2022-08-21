package jpashop.jpashop.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import jpashop.jpashop.dto.item.form.ItemEditDTO;
import org.hibernate.annotations.Parameter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class BookTest {
    private Book book;

    @BeforeEach
    public void init() {
        book = Book.builder()
            .name("test")
            .price(999)
            .stockQuantity(888)
            .author("testAuthor")
            .isbn("testISBN")
            .build();
    }

    @Test
    public void 업데이트() {
        //given
        ItemEditDTO itemEditDTO = new ItemEditDTO(0L, "testBookEdit", 9, 8, "BOOK", "testAuthorEdit", "testISBNEDIT",
            null,
            null, null, null);
        //when
        book.editItem(itemEditDTO);
        //then
        assertAll(
            () -> assertThat(book.getName()).isEqualTo(itemEditDTO.getName()),
            () -> assertThat(book.getPrice()).isEqualTo(itemEditDTO.getPrice()),
            () -> assertThat(book.getStockQuantity()).isEqualTo(itemEditDTO.getQuantity()),
            () -> assertThat(book.getAuthor()).isEqualTo(itemEditDTO.getAuthor()),
            () -> assertThat(book.getIsbn()).isEqualTo(itemEditDTO.getIsbn())
        );
    }


    @ParameterizedTest
    @ValueSource(ints = {0,-1})
    public void 가격은0이하가될수없다(int price) {
        //given
        //given
        ItemEditDTO itemEditDTO = new ItemEditDTO(0L, "testBookEdit", price, 8, "BOOK", "testAuthorEdit", "testISBNEDIT",
            null,
            null, null, null);
        //when&then
        assertThatThrownBy(() -> book.editItem(itemEditDTO))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("가격은 0 보다 커야합니다");
    }

    @ParameterizedTest
    @ValueSource(ints = {0,-1})
    public void 수량은0이하가될수없다(int quantity) {
        //given
        ItemEditDTO itemEditDTO = new ItemEditDTO(0L, "testBookEdit", 9, quantity, "BOOK", "testAuthorEdit", "testISBNEDIT",
            null,
            null, null, null);
        //when&then
        assertThatThrownBy(() -> book.editItem(itemEditDTO))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("수량은 0보다 커야합니다");
    }
}