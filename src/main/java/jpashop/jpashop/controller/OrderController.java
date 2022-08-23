package jpashop.jpashop.controller;

import java.util.LinkedList;
import jpashop.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    @GetMapping("")
    public String orderPage(Model model) {
        model.addAttribute("orders", orderService.getList());
        return "basic/orders";
    }
}
