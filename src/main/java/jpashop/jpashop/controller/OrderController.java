package jpashop.jpashop.controller;

import java.util.List;
import java.util.stream.Collectors;
import jpashop.jpashop.dto.order.OrderDTO;
import jpashop.jpashop.dto.order.form.OrderAddDTOList;
import jpashop.jpashop.service.ItemService;
import jpashop.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<OrderDTO> save(@Validated @RequestBody OrderAddDTOList orderAddDTOList,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "basic/orderAddForm";
        }
        HttpSession session = request.getSession(false);
        Long memberId = (Long) session.getAttribute("MEMBERID");
        orderService.saveOrder(orderAddDTO, memberId);

        return "redirect:/orders";
    }
}
