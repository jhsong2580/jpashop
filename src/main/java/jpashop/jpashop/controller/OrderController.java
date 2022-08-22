package jpashop.jpashop.controller;

import java.util.LinkedList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @GetMapping("")
    public String orderPage(Model model) {
        model.addAttribute("orders", new LinkedList<>());
        return "basic/orders";
    }
}
