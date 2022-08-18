package jpashop.jpashop.service;

import java.util.List;
import java.util.stream.Collectors;
import jpashop.jpashop.dto.item.ItemDTO;
import jpashop.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<ItemDTO> getList() {
        return itemRepository.findAll()
            .stream()
            .map(ItemDTO::from)
            .collect(Collectors.toList());
    }
}
