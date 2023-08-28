package uz.ecommerce.productservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.ecommerce.commons.model.request.ProductRequest;
import uz.ecommerce.commons.model.response.ProductResponse;
import uz.ecommerce.productservice.service.ProductService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> addProduct(
            @RequestBody ProductRequest request
    ) {
        return new ResponseEntity<>(
                productService.addProduct(request),
                CREATED
        );
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ok(productService.getAllProducts());
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getProductsList(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ok(productService.getProductsList(page, size));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(
            @PathVariable int productId
    ) {
        return ok(productService.getProductById(productId));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable int productId,
            @RequestBody ProductRequest request
    ) {
        return ok(productService.updateProduct(productId, request));
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(OK)
    public void deleteProduct(@PathVariable int productId) {
        productService.deleteProduct(productId);
    }
}
