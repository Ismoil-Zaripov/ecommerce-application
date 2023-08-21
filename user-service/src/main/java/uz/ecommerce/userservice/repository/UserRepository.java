package uz.ecommerce.userservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import uz.ecommerce.commons.constant.Role;
import uz.ecommerce.userservice.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, PagingAndSortingRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    Optional<User> findByIdAndRole(int id, Role role);

    Page<User> findAllByRole(Role role, Pageable pageable);

}
