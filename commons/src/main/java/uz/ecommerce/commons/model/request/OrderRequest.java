package uz.ecommerce.commons.model.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderRequest {
    private Integer customerId;
    private String orderDescription;
    private Double totalPrice;
    private List<Integer> products;
}
