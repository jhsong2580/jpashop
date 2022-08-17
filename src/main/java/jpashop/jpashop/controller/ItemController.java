package jpashop.jpashop.controller;

import java.util.LinkedList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/items")
public class ItemController {

    @GetMapping
    public String getItems(Model model) {
        model.addAttribute("items", new LinkedList<>());
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
