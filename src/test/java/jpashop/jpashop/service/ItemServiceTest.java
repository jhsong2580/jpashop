package jpashop.jpashop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import jpashop.jpashop.domain.Album;
import jpashop.jpashop.domain.Book;
import jpashop.jpashop.domain.Movie;
import jpashop.jpashop.dto.item.ItemDTO;
import jpashop.jpashop.dto.item.form.ItemAddDTO;
import jpashop.jpashop.repository.AlbumRepository;
import jpashop.jpashop.repository.BookRepository;
import jpashop.jpashop.repository.ItemRepository;
import jpashop.jpashop.repository.MovieRepository;
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
    public void 아이템리스트가져오기() {
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
    public void 앨범저장하기() {
        //given
        ItemAddDTO itemAddDTO = new ItemAddDTO(null, null, null, "ALBUM", null, null, null, null,
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
    public void 책저장하기() {
        //given
        ItemAddDTO itemAddDTO = new ItemAddDTO(null, null, null, "BOOK", null, null, null, null,
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
    public void 영화저장하기() {
        //given
        ItemAddDTO itemAddDTO = new ItemAddDTO(null, null, null, "MOVIE", null, null, null, null,
            null,
            null);
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
}