package jpashop.jpashop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jpashop.jpashop.dto.order.form.OrderAddDTO;
import jpashop.jpashop.service.ItemService;
import jpashop.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
    public String getOrderPage(@ModelAttribute OrderAddDTOList orderAddDTOList, Model model) {
        List<ItemDTO> items = itemService.getList();
        model.addAttribute("items", items);

        return "basic/orderAddForm";
    }

    @PostMapping()
    public String saveOrder(@Validated @ModelAttribute OrderAddDTO orderAddDTO,
        BindingResult bindingResult,
        HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "basic/orderAddForm";
        }
        HttpSession session = request.getSession(false);
        Long memberId = (Long) session.getAttribute("MEMBERID");
        orderService.saveOrder(orderAddDTO, memberId);

        return "redirect:/orders";
    }
}
