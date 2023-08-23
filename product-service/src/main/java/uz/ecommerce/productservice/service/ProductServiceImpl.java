package uz.ecommerce.productservice.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.ecommerce.commons.exception.APIException;
import uz.ecommerce.commons.model.request.ProductRequest;
import uz.ecommerce.commons.model.response.ProductResponse;
import uz.ecommerce.productservice.entity.Product;
import uz.ecommerce.productservice.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Override
    public ProductResponse addProduct(ProductRequest request) {
        boolean productIsExists = productRepository.findByName(request.getName()).isPresent();
        if (productIsExists) throw new APIException(
                "Product already exists",
                HttpStatus.BAD_REQUEST.value()
        );

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .imageUrl(request.getImageUrl())
                .quantity(request.getQuantity())
                .build();

        productRepository.save(product);

        return mapToResponse(product);
    }

    @Override
    public Page<ProductResponse> getProductsList(int page, int size) {
        return productRepository
                .findAll(PageRequest.of(page, size))
                .map(this::mapToResponse);
    }

    @Override
    public ProductResponse getProductById(int productId) {
        Product product = getProduct(productId);
        return mapToResponse(product);
    }

    @Override
    public ProductResponse updateProduct(int productId, ProductRequest request) {
        Product product = getProduct(productId);

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setImageUrl(request.getImageUrl());
        product.setQuantity(request.getQuantity());

        productRepository.save(product);
        return mapToResponse(product);
    }

    @Override
    public void deleteProduct(int productId) {
        Product product = getProduct(productId);
        productRepository.delete(product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private Product getProduct(int productId) {
        return productRepository
                .findById(productId)
                .orElseThrow(() -> new APIException(
                        "Product not found", 404
                ));
    }

    private ProductResponse mapToResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        BeanUtils.copyProperties(product, productResponse);
        return productResponse;
    }
}
