package uz.ecommerce.authenticationservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import uz.ecommerce.commons.config.FeignClientConfig;
import uz.ecommerce.commons.model.request.UserRequest;
import uz.ecommerce.commons.model.response.UserResponse;

@FeignClient(name = "USER-SERVICE/api/v1/user", configuration = FeignClientConfig.class)
public interface UserClient {

    @PostMapping
    ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request);
    @GetMapping("/{username}")
    ResponseEntity<UserResponse> getByUsername(@PathVariable String username);
}
