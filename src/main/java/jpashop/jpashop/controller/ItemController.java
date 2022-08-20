package jpashop.jpashop.controller;

import jpashop.jpashop.domain.ItemType;
import jpashop.jpashop.dto.item.form.ItemAddDTO;
import jpashop.jpashop.dto.item.form.ItemValidationGroups;
import jpashop.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public String getItems(Model model) {
        model.addAttribute("items", itemService.getList());
        return "basic/items";
    }

    @GetMapping("{id}")
    public String getItemDetail(@PathVariable(name = "id") Long itemId, Model model) {
        model.addAttribute("itemDTO", itemService.findById(itemId));

        return "basic/item";
    }

    @GetMapping("/add")
    public String addItem(@ModelAttribute ItemAddDTO itemAddDTO, Model model) {
        model.addAttribute("itemTypes", ItemType.getItemTypeNamesForPage());
        return "basic/addForm";
    }

    @PostMapping("/album")
    public String saveAlbumItem(
        @Validated(ItemValidationGroups.albumGroup.class) @ModelAttribute ItemAddDTO itemAddDTO,
        BindingResult bindingResult, Model model) {

        model.addAttribute("itemTypes", ItemType.getItemTypeNamesForPage());
        if (bindingResult.hasErrors()) {
            return "basic/addForm";
        }
        itemService.save(itemAddDTO);
        return "redirect:/items";
    }

    @PostMapping("/book")
    public String saveBookItem(
        @Validated(ItemValidationGroups.bookGroup.class) @ModelAttribute ItemAddDTO itemAddDTO,
        BindingResult bindingResult, Model model) {

        model.addAttribute("itemTypes", ItemType.getItemTypeNamesForPage());
        if (bindingResult.hasErrors()) {
            return "basic/addForm";
        }
        itemService.save(itemAddDTO);
        return "redirect:/items";
    }

    @PostMapping("/movie")
    public String saveMovieItem(
        @Validated(ItemValidationGroups.movieGroup.class) @ModelAttribute ItemAddDTO itemAddDTO,
        BindingResult bindingResult, Model model) {

        model.addAttribute("itemTypes", ItemType.getItemTypeNamesForPage());
        if (bindingResult.hasErrors()) {
            return "basic/addForm";
        }
        itemService.save(itemAddDTO);
        return "redirect:/items";
    }

    @PatchMapping
    public String modifyItem() {
        return "redirect:/items";
    }

}
