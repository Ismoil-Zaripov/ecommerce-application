package uz.ecommerce.apigateway.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import uz.ecommerce.apigateway.filter.JwtAuthenticationFilter;
import uz.ecommerce.apigateway.model.UserDetailsImpl;
import uz.ecommerce.commons.model.response.UserResponse;

import static org.springframework.http.HttpStatus.valueOf;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final WebClient.Builder webClient;
    @Bean
    public SecurityWebFilterChain filterChain(
            ServerHttpSecurity http,
            ReactiveAuthenticationManager authenticationManager,
            @Lazy JwtAuthenticationFilter jwtAuthenticationFilter
            ) {
        return http
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .authenticationManager(authenticationManager)
                .authorizeExchange(exchange -> {
                    exchange.pathMatchers("/api/v1/authentication/**").permitAll()
                            .anyExchange().authenticated();
                })
                .addFilterBefore(jwtAuthenticationFilter, SecurityWebFiltersOrder.HTTP_BASIC)
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint((exchange, exception) ->
                                Mono.fromRunnable(() ->
                                        exchange.getResponse().setStatusCode(valueOf(401)))))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService() throws UsernameNotFoundException {
        return username -> webClient
                .build()
                .get()
                .uri("http://USER-SERVICE/api/v1/user/" + username)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .map(UserDetailsImpl::new);
    }

    @Bean
    public ReactiveAuthenticationManager authenticationManager(
            ReactiveUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {

        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager =
                new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);

        authenticationManager.setPasswordEncoder(passwordEncoder);
        return authenticationManager;
    }
}
