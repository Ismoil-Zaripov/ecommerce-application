package uz.ecommerce.commons.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ecommerce.commons.constant.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Integer id;
    private UserResponse customer;
    private OrderStatus orderStatus;
    private String orderDescription;
    private Double totalPrice;
    private List<ProductResponse> products;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
