package jpashop.jpashop.controller;

import java.util.List;
import java.util.stream.Collectors;
import jpashop.jpashop.dto.order.DeliveryEditDTO;
import jpashop.jpashop.dto.order.OrderAddDTOList;
import jpashop.jpashop.dto.order.OrderDTO;
import jpashop.jpashop.dto.order.OrderEditDTO;
import jpashop.jpashop.service.ItemService;
import jpashop.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping(value = "", consumes = "application/json")
    public ResponseEntity<List<OrderDTO>> orderList() {
        return ResponseEntity.ok(orderService.getList());
    }

    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<OrderDTO> save(@Validated @RequestBody OrderAddDTOList orderAddDTOList,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(bindingResult.getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toSet())
                .toString());
        }
        return ResponseEntity.ok(orderService.saveOrder(orderAddDTOList, 1L));
    }

    @PutMapping(value = "{id}", consumes = "application/json")
    public ResponseEntity<OrderDTO> editOrder(@PathVariable(name = "id") Long orderId,
        @Validated @RequestBody OrderEditDTO orderEditDTO,
        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(bindingResult.getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toSet())
                .toString());
        }

        return ResponseEntity.ok(orderService.changeOrderStatus(orderId, orderEditDTO));
    }

    @PutMapping(value = "/deliveries/{id}", consumes = "application/json")
    public ResponseEntity<OrderDTO> editDelivery(@PathVariable(name = "id") Long orderId,
        @Validated @RequestBody DeliveryEditDTO deliveryEditDTO,
        BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new IllegalArgumentException(bindingResult.getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toSet())
                .toString());
        }

        return ResponseEntity.ok(orderService.editDelivery(orderId, deliveryEditDTO));
    }
}
