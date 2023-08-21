package uz.ecommerce.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import uz.ecommerce.commons.config.FeignClientConfig;
import uz.ecommerce.commons.model.request.AuthenticationRequest;

@FeignClient(
        name = "AUTHENTICATION-SERVICE/api/v1/authenticate",
        configuration = FeignClientConfig.class
)
public interface AuthenticationClient {
    @PostMapping
    void authenticate(@RequestBody AuthenticationRequest authenticationRequest);
}
