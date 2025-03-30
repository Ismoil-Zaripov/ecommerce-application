package uz.ecommerce.apigateway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import uz.ecommerce.apigateway.service.AuthenticationService;
import uz.ecommerce.apigateway.service.JwtService;
import uz.ecommerce.commons.model.request.AuthenticationRequest;
import uz.ecommerce.commons.model.request.RegisterRequest;
import uz.ecommerce.commons.model.response.AuthenticationResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;


    @PostMapping("/sign-in")
    public Mono<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return authenticationService.authenticate(request);
    }

    @PostMapping("/sign-up")
    public Mono<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return authenticationService.register(request);
    }

    @GetMapping("/is-token-valid")
    public Mono<Boolean> isTokenValid(@RequestParam String token) {
        return Mono.just(jwtService.isTokenValid(token));
    }
}