package jpashop.jpashop.controller;

import jpashop.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    public String getItemDetail(Model model) {
        model.addAttribute("item", null);
        return "basic/item";
    }

    @PostMapping
    public String saveItem() {
        return "redirect:/items";
    }

    @PatchMapping
    public String modifyItem() {
        return "redirect:/items";
    }

}
