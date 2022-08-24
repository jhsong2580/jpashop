package jpashop.jpashop.controller;

import jpashop.jpashop.dto.order.form.OrderAddDTO;
import jpashop.jpashop.service.ItemService;
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
    private final ItemService itemService;

    @GetMapping("")
    public String orderListPage(Model model) {
        model.addAttribute("orders", orderService.getList());
        return "basic/orders";
    }

    @GetMapping("/add")
    public String getOrderPage(Model model) {
        model.addAttribute("items", itemService.getList());
        model.addAttribute("order", new OrderAddDTO());

        return "basic/orderAddForm";
    }
}
