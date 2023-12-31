package uz.ecommerce.productservice.service;

import org.springframework.data.domain.Page;
import uz.ecommerce.commons.model.request.ProductRequest;
import uz.ecommerce.commons.model.response.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse addProduct(ProductRequest request);
    Page<ProductResponse> getProductsList(int page, int size);
    ProductResponse getProductById(int productId);
    ProductResponse updateProduct(int productId, ProductRequest request);
    void deleteProduct(int productId);
    List<ProductResponse> getAllProducts();
}