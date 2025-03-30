package uz.ecommerce.apigateway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import uz.ecommerce.commons.constant.Role;
import uz.ecommerce.commons.exception.APIException;
import uz.ecommerce.commons.model.request.AuthenticationRequest;
import uz.ecommerce.commons.model.request.RegisterRequest;
import uz.ecommerce.commons.model.request.UserRequest;
import uz.ecommerce.commons.model.response.AuthenticationResponse;
import uz.ecommerce.commons.model.response.UserResponse;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final ReactiveAuthenticationManager authenticationManager;
    private final ReactiveUserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final WebClient.Builder userClient;

    public Mono<AuthenticationResponse> authenticate(AuthenticationRequest authenticationRequest) {
        return Mono.fromCallable(() -> authenticate(
                                authenticationRequest.getUsername(),
                                authenticationRequest.getPassword()
                ))
                .then(userDetailsService.findByUsername(authenticationRequest.getUsername()))
                .map(userDetails -> new AuthenticationResponse(jwtService.generateToken(userDetails)))
                .onErrorMap(e -> new APIException("Bad credentials", HttpStatus.BAD_REQUEST.value()));
    }

    public Mono<AuthenticationResponse> register(RegisterRequest request) {
        UserRequest userRequest = UserRequest.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.CUSTOMER)
                .build();

        return userClient.build()
                .post()
                .uri("USER-SERVICE/api/v1/user")
                .bodyValue(userRequest)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .flatMap(userResponse -> Mono.fromCallable(() -> authenticate(
                        request.getUsername(),
                        request.getPassword()
                )))
                .then(userDetailsService.findByUsername(request.getUsername()))
                .map(userDetails -> new AuthenticationResponse(jwtService.generateToken(userDetails)))
                .onErrorMap(e -> new APIException("Bad credentials", HttpStatus.BAD_REQUEST.value()));
    }

    private Mono<Authentication> authenticate(String username, String password) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}