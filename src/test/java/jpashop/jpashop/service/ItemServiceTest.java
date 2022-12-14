package jpashop.jpashop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import jpashop.jpashop.domain.Album;
import jpashop.jpashop.domain.Book;
import jpashop.jpashop.domain.Movie;
import jpashop.jpashop.dto.item.ItemDTO;
import jpashop.jpashop.dto.item.form.ItemAddDTO;
import jpashop.jpashop.dto.item.form.ItemEditDTO;
import jpashop.jpashop.repository.AlbumRepository;
import jpashop.jpashop.repository.BookRepository;
import jpashop.jpashop.repository.ItemRepository;
import jpashop.jpashop.repository.MovieRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private AlbumRepository albumRepository;

    private Album album;
    private Movie movie;
    private Book book;

    @BeforeEach
    public void init() {
        itemService = new ItemService(itemRepository, bookRepository, movieRepository,
            albumRepository);
        album = Album.builder()
            .artist("artist")
            .stockQuantity(1)
            .name("albumName")
            .price(1)
            .etc("etc")
            .build();
        book = Book.builder()
            .name("bookName")
            .price(2)
            .stockQuantity(2)
            .author("author")
            .isbn("isbn")
            .build();
        movie = Movie.builder()
            .name("movieName")
            .price(3)
            .stockQuantity(3)
            .actor("actor")
            .director("director")
            .build();
    }

    @Test
    public void ??????????????????????????????() {
        //given
        when(itemRepository.findAll()).thenReturn(Arrays.asList(album, movie, book));
        //when
        List<ItemDTO> itemList = itemService.getList();
        //then
        assertAll(
            () -> assertThat(itemList).extracting("name")
                .containsExactly("albumName", "movieName", "bookName"),
            () -> assertThat(itemList).extracting("price")
                .containsExactly(1, 3, 2)
        );
    }

    @Test
    public void ??????????????????() {
        //given
        ItemAddDTO itemAddDTO = new ItemAddDTO(album.getName(), album.getPrice(), album.getStockQuantity(), "ALBUM", null, null, "artist", null,
            null,
            null);
        when(albumRepository.save(any())).thenReturn(album);

        //when
        ItemDTO itemDTO = itemService.save(itemAddDTO);

        //then
        assertAll(
            () -> assertThat(itemDTO.getPrice()).isEqualTo(album.getPrice()),
            () -> assertThat(itemDTO.getArtist()).isEqualTo(album.getArtist()),
            () -> assertThat(itemDTO.getEtc()).isEqualTo(album.getEtc())
        );
    }

    @Test
    public void ???????????????() {
        //given
        ItemAddDTO itemAddDTO = new ItemAddDTO(book.getName(), book.getPrice(),
            book.getStockQuantity(), "BOOK", "author", "24612", null, null,
            null,
            null);
        when(bookRepository.save(any())).thenReturn(book);

        //when
        ItemDTO itemDTO = itemService.save(itemAddDTO);

        //then
        assertAll(
            () -> assertThat(itemDTO.getPrice()).isEqualTo(book.getPrice()),
            () -> assertThat(itemDTO.getIsbn()).isEqualTo(book.getIsbn()),
            () -> assertThat(itemDTO.getAuthor()).isEqualTo(book.getAuthor())
        );
    }

    @Test
    public void ??????????????????() {
        //given
        ItemAddDTO itemAddDTO = new ItemAddDTO(movie.getName(), movie.getPrice(), movie.getStockQuantity(), "MOVIE", null, null, null, null,
            "director",
            "actor");
        when(movieRepository.save(any())).thenReturn(movie);

        //when
        ItemDTO itemDTO = itemService.save(itemAddDTO);

        //then
        assertAll(
            () -> assertThat(itemDTO.getPrice()).isEqualTo(movie.getPrice()),
            () -> assertThat(itemDTO.getDirector()).isEqualTo(movie.getDirector()),
            () -> assertThat(itemDTO.getActor()).isEqualTo(movie.getActor())
        );
    }

    @Test
    public void ???????????? (){
        //given
        ItemEditDTO itemEditDTO = new ItemEditDTO(1L, "editName", 999, 888, "MOVIE", null, null,
            null, null, "editDirector",
            "editActor");
        when(itemRepository.findById(1L)).thenReturn(Optional.of(movie));

        //when
        itemService.edit(itemEditDTO, 1L);

        //then
        assertAll(
            () -> assertThat(movie.getName()).isEqualTo(itemEditDTO.getName()),
            () -> assertThat(movie.getPrice()).isEqualTo(itemEditDTO.getPrice()),
            () -> assertThat(movie.getActor()).isEqualTo(itemEditDTO.getActor())
        );
    }

    @Test
    public void ?????????_???????????????_???????????? (){
        //given
        ItemEditDTO itemEditDTO = new ItemEditDTO(1L, "editName", 999, 888, "MOVIE", null, null,
            null, null, "editDirector",
            "editActor");

        //when & then
        assertThatThrownBy(() -> itemService.edit(itemEditDTO, 2L))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("????????? ?????? ???????????????");
    }

    @Test
    public void ??????_?????????_???????????? (){
        //given
        ItemEditDTO itemEditDTO = new ItemEditDTO(1L, "editName", 999, 888, "MOVIE", null, null,
            null, null, "editDirector",
            "editActor");

        //when & then
        assertThatThrownBy(() -> itemService.edit(itemEditDTO, 1L))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("????????? Item ID ?????????");
    }
}