package uz.ecommerce.commons.model.request;

import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private String description;
    private String imageUrl;
    private Integer quantity;
    private Double price;
}
