package uz.ecommerce.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import uz.ecommerce.commons.config.FeignClientConfig;
import uz.ecommerce.commons.model.response.ProductResponse;

import java.util.List;

@FeignClient(name = "PRODUCT-SERVICE/api/v1/product", configuration = FeignClientConfig.class)
public interface ProductClient {
    @GetMapping("/all")
    ResponseEntity<List<ProductResponse>> getAllProducts();
}
