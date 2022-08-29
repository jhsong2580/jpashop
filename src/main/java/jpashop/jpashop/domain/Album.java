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
@DiscriminatorValue(value = "A")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@PrimaryKeyJoinColumn(
    name = "ALBUM_ID"
)
public class Album extends Item {

    private String artist;
    private String etc;

    @Builder
    protected Album(String name, Integer price, Integer stockQuantity, String artist, String etc) {
        super(name, price, stockQuantity);
        this.artist = artist;
        this.etc = etc;
    }

    public static Album from(ItemAddDTO itemAddDTO) {
        if (isEmpty(itemAddDTO.getArtist())) {
            throw new IllegalArgumentException("앨범은 아티스트 정보가 있어야합니다");
        }
        return Album.builder()
            .artist(itemAddDTO.getArtist())
            .etc(itemAddDTO.getEtc())
            .price(itemAddDTO.getPrice())
            .stockQuantity(itemAddDTO.getQuantity())
            .name(itemAddDTO.getName())
            .build();
    }

    public void editItem(ItemEditDTO itemEditDTO) {
        if (isEmpty(itemEditDTO.getArtist())) {
            throw new IllegalArgumentException("앨범은 아티스트 정보가 있어야합니다");
        }
        super.editItem(itemEditDTO);
        this.artist = itemEditDTO.getArtist();
        this.etc = itemEditDTO.getEtc();
    }
}
