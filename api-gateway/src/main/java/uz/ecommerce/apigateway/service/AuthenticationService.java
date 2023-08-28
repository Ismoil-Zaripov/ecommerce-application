package uz.ecommerce.apigateway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()
            ));

            return userDetailsService
                    .findByUsername(authenticationRequest.getUsername())
                    .map(userDetails -> {

                        String token = jwtService.generateToken(userDetails);
                        return new AuthenticationResponse(token);
                    });


        } catch (Exception e) {
            throw new APIException(
                    "Bad credentials",
                    HttpStatus.BAD_REQUEST.value()
            );
        }
    }

    public Mono<AuthenticationResponse> register(RegisterRequest request) {

        try {

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
                    .flatMap(userResponse -> {

                        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()
                        ));

                        return userDetailsService.findByUsername(request.getUsername())
                                .map(userDetails -> {
                                    String token = jwtService.generateToken(userDetails);
                                    return new AuthenticationResponse(token);
                                });
                    });


        } catch (Exception e) {
            e.printStackTrace();
            throw new APIException(
                    "Bad credentials",
                    HttpStatus.BAD_REQUEST.value()
            );
        }
    }

}
