package jpashop.jpashop.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import jpashop.jpashop.dto.item.form.ItemEditDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AlbumTest {

    private Album album;

    @BeforeEach
    public void init() {
        album = Album.builder()
            .name("testAlbum")
            .price(999)
            .stockQuantity(888)
            .etc("testEtc")
            .artist("testArtist")
            .build();
    }

    @Test
    public void 업데이트() {
        //given
        ItemEditDTO itemEditDTO = new ItemEditDTO(0L, "testAlbumEdit", 9, 8, "ALBUM", null, null,
            "testArtistEdit",
            "testEtcEdit", null, null);
        //when
        album.editItem(itemEditDTO);
        //then
        assertAll(
            () -> assertThat(album.getName()).isEqualTo(itemEditDTO.getName()),
            () -> assertThat(album.getPrice()).isEqualTo(itemEditDTO.getPrice()),
            () -> assertThat(album.getStockQuantity()).isEqualTo(itemEditDTO.getQuantity()),
            () -> assertThat(album.getArtist()).isEqualTo(itemEditDTO.getArtist()),
            () -> assertThat(album.getEtc()).isEqualTo(itemEditDTO.getEtc())
        );
    }

    @Test
    public void 가격은0이하가될수없다() {
        //given
        ItemEditDTO itemEditDTO = new ItemEditDTO(0L, "testAlbumEdit", -1, 8, "ALBUM", null, null,
            "testArtistEdit",
            "testEtcEdit", null, null);
        //when&then
        assertThatThrownBy(() -> album.editItem(itemEditDTO))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("가격은 0 보다 커야합니다");
    }

    @Test
    public void 수량은0이하가될수없다() {
        //given
        ItemEditDTO itemEditDTO = new ItemEditDTO(0L, "testAlbumEdit", 1, -1, "ALBUM", null, null,
            "testArtistEdit",
            "testEtcEdit", null, null);
        //when&then
        assertThatThrownBy(() -> album.editItem(itemEditDTO))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("수량은 0보다 커야합니다");
    }
}