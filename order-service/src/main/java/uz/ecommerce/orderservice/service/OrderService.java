package uz.ecommerce.orderservice.service;

import org.springframework.data.domain.Page;
import uz.ecommerce.commons.model.request.OrderRequest;
import uz.ecommerce.commons.model.response.OrderResponse;

public interface OrderService {
    OrderResponse getOrderById(int orderId);
    OrderResponse placeOrder(OrderRequest request);
    Page<OrderResponse> getOrders(int page, int size);
}
