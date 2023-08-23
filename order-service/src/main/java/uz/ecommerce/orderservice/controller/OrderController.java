package uz.ecommerce.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.ecommerce.commons.model.request.OrderRequest;
import uz.ecommerce.commons.model.response.OrderResponse;
import uz.ecommerce.orderservice.service.OrderService;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place-order")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest request) {
        return new ResponseEntity<>(
                orderService.placeOrder(request),
                CREATED
        );
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable int orderId) {
        return ok(orderService.getOrderById(orderId));
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponse>> getOrders(
            @RequestParam int page,
            @RequestParam int size
    ){
        return ok(orderService.getOrders(page,size));
    }
}
