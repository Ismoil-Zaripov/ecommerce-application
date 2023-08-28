package uz.ecommerce.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import uz.ecommerce.productservice.entity.Product;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, PagingAndSortingRepository<Product, Integer> {
    Optional<Product> findByName(String productName);
}