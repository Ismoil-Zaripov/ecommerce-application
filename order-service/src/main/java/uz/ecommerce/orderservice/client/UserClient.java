package uz.ecommerce.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import uz.ecommerce.commons.config.FeignClientConfig;
import uz.ecommerce.commons.model.response.UserResponse;

@FeignClient(name = "USER-SERVICE/api/v1/user", configuration = FeignClientConfig.class)
public interface UserClient {
    @GetMapping("/{userId}")
    ResponseEntity<UserResponse> getUserById(@PathVariable int userId);
}
