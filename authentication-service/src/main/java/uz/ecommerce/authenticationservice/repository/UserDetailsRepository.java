package uz.ecommerce.authenticationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.ecommerce.authenticationservice.entity.UserDetailsImpl;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetailsImpl, Integer> {
    Optional<UserDetailsImpl> findByUsername(String username);
}
