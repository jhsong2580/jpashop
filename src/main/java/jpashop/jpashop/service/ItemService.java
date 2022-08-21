package jpashop.jpashop.service;

import java.util.List;
import java.util.stream.Collectors;
import jpashop.jpashop.domain.Album;
import jpashop.jpashop.domain.Book;
import jpashop.jpashop.domain.Item;
import jpashop.jpashop.domain.ItemType;
import jpashop.jpashop.domain.Movie;
import jpashop.jpashop.dto.item.ItemDTO;
import jpashop.jpashop.dto.item.form.ItemAddDTO;
import jpashop.jpashop.dto.item.form.ItemEditDTO;
import jpashop.jpashop.repository.AlbumRepository;
import jpashop.jpashop.repository.BookRepository;
import jpashop.jpashop.repository.ItemRepository;
import jpashop.jpashop.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;
    private final BookRepository bookRepository;
    private final MovieRepository movieRepository;
    private final AlbumRepository albumRepository;

    public List<ItemDTO> getList() {
        return itemRepository.findAll()
            .stream()
            .map(ItemDTO::from)
            .collect(Collectors.toList());
    }

    @Transactional
    public ItemDTO save(ItemAddDTO itemAddDTO) {
        if (itemAddDTO.getDType().equals(ItemType.MOVIE.getWebPageDype())) {
            return ItemDTO.from(save(Movie.from(itemAddDTO)));
        }
        if (itemAddDTO.getDType().equals(ItemType.ALBUM.getWebPageDype())) {
            return ItemDTO.from(save(Album.from(itemAddDTO)));
        }
        if (itemAddDTO.getDType().equals(ItemType.BOOK.getWebPageDype())) {
            return ItemDTO.from(save(Book.from(itemAddDTO)));
        }
        throw new IllegalArgumentException("잘못된 Item Type입니다");
    }

    private Item save(Book book) {
        return bookRepository.save(book);
    }

    private Item save(Album album) {
        return albumRepository.save(album);
    }

    private Item save(Movie movie) {
        return movieRepository.save(movie);
    }

    public ItemDTO findById(Long itemId){
        Item item = itemRepository.findById(itemId)
            .orElseThrow(() -> new IllegalArgumentException("잘못된 Item ID 입니다"));

        return ItemDTO.from(item);
    }

    public ItemEditDTO findByIdForEdit(Long itemId) {
        Item item = itemRepository.findById(itemId)
            .orElseThrow(() -> new IllegalArgumentException("잘못된 Item ID 입니다"));

        return ItemEditDTO.from(item);
    }

    @Transactional
    public void edit(ItemEditDTO itemEditDTO, Long itemId) {
        if(itemId != itemEditDTO.getItemId()){
            throw new IllegalArgumentException("잘못된 수정 접근입니다");
        }

        Item item = itemRepository.findById(itemId)
            .orElseThrow(() -> new IllegalArgumentException("잘못된 Item ID 입니다"));

        if(item instanceof Book){
            ((Book) item).editItem(itemEditDTO);
        }
        if(item instanceof Album){
            ((Album) item).editItem(itemEditDTO);
        }
        if(item instanceof Movie){
            ((Movie) item).editItem(itemEditDTO);
        }
    }
}
