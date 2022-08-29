package jpashop.jpashop.controller;

import java.util.List;
import jpashop.jpashop.dto.order.OrderDTO;
import jpashop.jpashop.service.ItemService;
import jpashop.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ItemService itemService;

    @GetMapping(value = "", consumes = "application/json")
    public ResponseEntity<List<OrderDTO>> orderList() {
        return ResponseEntity.ok(orderService.getList());
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
