package jpashop.jpashop.controller;

import java.util.List;
import jpashop.jpashop.dto.item.ItemDTO;
import jpashop.jpashop.dto.item.form.ItemAddDTO;
import jpashop.jpashop.dto.item.form.ItemEditDTO;
import jpashop.jpashop.dto.item.form.ItemValidationGroups;
import jpashop.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping(consumes = "application/json")
    public ResponseEntity<List<ItemDTO>> getItemList () {
        return ResponseEntity.ok(itemService.getList());
    }

    @GetMapping(value = "{id}", consumes = "application/json")
    public ResponseEntity<ItemDTO> getItemDetail(@PathVariable(name = "id") Long itemId) {
        return ResponseEntity.ok(itemService.findById(itemId));
    }

    @PatchMapping(value = "{id}", consumes = "application/json")
    public ResponseEntity<ItemDTO> editBookItem(@PathVariable(name = "id") Long itemId,
        @Validated({ItemValidationGroups.defaultGroup.class}) @RequestBody ItemEditDTO itemEditDTO,
        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(bindingResult.getFieldErrors().toString());
        }

        return ResponseEntity.ok().body(itemService.edit(itemEditDTO, itemId));
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ItemDTO> save(
        @Validated(ItemValidationGroups.defaultGroup.class) @RequestBody ItemAddDTO itemAddDTO,
        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(bindingResult.getFieldErrors().toString());
        }
        return ResponseEntity.ok(itemService.save(itemAddDTO));
    }
}
