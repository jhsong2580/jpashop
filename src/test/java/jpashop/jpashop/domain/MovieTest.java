package jpashop.jpashop.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import jpashop.jpashop.dto.item.form.ItemEditDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MovieTest {
    private Movie movie;

    @BeforeEach
    public void init() {
        movie = Movie.builder()
            .name("test")
            .price(999)
            .stockQuantity(888)
            .actor("testActor")
            .director("testDirector")
            .build();

    }

    @Test
    public void 업데이트() {
        //given
        ItemEditDTO itemEditDTO = new ItemEditDTO(0L, "testBookEdit", 9, 8, "MOVIE", null, null,
            null,
            null, "testDirectorEdit", "testActorEdit");
        //when
        movie.editItem(itemEditDTO);
        //then
        assertAll(
            () -> assertThat(movie.getName()).isEqualTo(itemEditDTO.getName()),
            () -> assertThat(movie.getPrice()).isEqualTo(itemEditDTO.getPrice()),
            () -> assertThat(movie.getStockQuantity()).isEqualTo(itemEditDTO.getQuantity()),
            () -> assertThat(movie.getDirector()).isEqualTo(itemEditDTO.getDirector()),
            () -> assertThat(movie.getActor()).isEqualTo(itemEditDTO.getActor())
        );
    }


    @ParameterizedTest
    @ValueSource(ints = {0,-1})
    public void 가격은0이하가될수없다(int price) {
        //given
        ItemEditDTO itemEditDTO = new ItemEditDTO(0L, "testBookEdit", price, 8, "MOVIE", null, null,
            null,
            null, "testDirectorEdit", "testActorEdit");
        //when&then
        assertThatThrownBy(() -> movie.editItem(itemEditDTO))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("가격은 0 보다 커야합니다");
    }

    @ParameterizedTest
    @ValueSource(ints = {-999,-1})
    public void 수량은_음수가_될수없다(int quantity) {
        //given
        ItemEditDTO itemEditDTO = new ItemEditDTO(0L, "testBookEdit", 9, quantity, "MOVIE", null, null,
            null,
            null, "testDirectorEdit", "testActorEdit");
        //when&then
        assertThatThrownBy(() -> movie.editItem(itemEditDTO))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("수량은 0보다 커야합니다");
    }
}