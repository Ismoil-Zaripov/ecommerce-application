package uz.ecommerce.orderservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import uz.ecommerce.commons.constant.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "customer_id")
    private Integer customerId;
    private OrderStatus orderStatus;
    private String orderDescription;
    @Column(name = "total_price")
    private Double totalPrice;
    @ElementCollection
    private List<Integer> products;
    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_date")
    private LocalDateTime updatedAt;
}
