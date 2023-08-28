package uz.ecommerce.apigateway.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import uz.ecommerce.apigateway.service.JwtService;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {
    private final JwtService jwtService;
    private final ReactiveUserDetailsService userDetailsService;

    @Override
    public Mono<Void> filter(
            @NonNull ServerWebExchange exchange,
            @NonNull WebFilterChain chain
    ) {
        String token = resolveToken(exchange.getRequest());

        if (StringUtils.hasText(token) && jwtService.isTokenValid(token)) {

            String username = jwtService.extractUsername(token);

            return userDetailsService
                    .findByUsername(username)
                    .map(userDetails -> new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    ))
                    .subscribeOn(Schedulers.boundedElastic())
                    .flatMap(authentication -> chain.filter(exchange).contextWrite(
                            ReactiveSecurityContextHolder
                                    .withAuthentication(authentication)
                    ));

        }
        return chain.filter(exchange);
    }

    private String resolveToken(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
