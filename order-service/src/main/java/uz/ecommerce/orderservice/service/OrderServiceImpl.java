package uz.ecommerce.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.ecommerce.commons.constant.OrderStatus;
import uz.ecommerce.commons.exception.APIException;
import uz.ecommerce.commons.model.request.OrderRequest;
import uz.ecommerce.commons.model.response.OrderResponse;
import uz.ecommerce.commons.model.response.ProductResponse;
import uz.ecommerce.commons.model.response.UserResponse;
import uz.ecommerce.orderservice.client.ProductClient;
import uz.ecommerce.orderservice.client.UserClient;
import uz.ecommerce.orderservice.entity.Order;
import uz.ecommerce.orderservice.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserClient userClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;

    @Override
    public OrderResponse getOrderById(int orderId) {
        Order order = getOrder(orderId);
        return mapToResponse(order);
    }

    @Override
    public OrderResponse placeOrder(OrderRequest request) {

        Order order = Order.builder()
                .customerId(request.getCustomerId())
                .orderDescription(request.getOrderDescription())
                .totalPrice(request.getTotalPrice())
                .products(request.getProducts())
                .orderStatus(OrderStatus.SUCCESS)
                .build();

        orderRepository.save(order);
        return mapToResponse(order);
    }

    @Override
    public Page<OrderResponse> getOrders(int page, int size) {
        return orderRepository
                .findAll(PageRequest.of(page,size))
                .map(this::mapToResponse);
    }

    private OrderResponse mapToResponse(Order order) {
        List<ProductResponse> orderedProducts = orderedProducts(order);
        UserResponse customer = userClient.getUserById(order.getCustomerId()).getBody();

        return OrderResponse.builder()
                .id(order.getId())
                .customer(customer)
                .orderStatus(order.getOrderStatus())
                .orderDescription(order.getOrderDescription())
                .totalPrice(order.getTotalPrice())
                .products(orderedProducts)
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

    private List<ProductResponse> orderedProducts(Order order) {
        List<ProductResponse> productResponses = productClient.getAllProducts().getBody();
        List<ProductResponse> orderedProductResponses = new ArrayList<>();

        for (ProductResponse productResponse : productResponses) {
            for (Integer productId : order.getProducts()) {
                if (productResponse.getId().equals(productId)) {
                    orderedProductResponses.add(productResponse);
                }
            }
        }

        return orderedProductResponses;
    }

    private Order getOrder(int orderId) {
        return orderRepository
                .findById(orderId)
                .orElseThrow(() -> new APIException(
                        "Order not found", 404
                ));
    }
}