package uz.ecommerce.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import uz.ecommerce.orderservice.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>, PagingAndSortingRepository<Order, Integer> {

}