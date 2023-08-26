package uz.ecommerce.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uz.ecommerce.authenticationservice.filter.JwtRequestFilter;
import uz.ecommerce.authenticationservice.service.jwt.JwtService;
import uz.ecommerce.authenticationservice.service.jwt.JwtServiceImpl;
import uz.ecommerce.commons.exception.APIExceptionHandler;

@Configuration
public class ApplicationConfig {
    @Bean
    public ResponseEntityExceptionHandler apiExceptionHandler(){
        return new APIExceptionHandler();
    }
    @Bean
    public OncePerRequestFilter requestFilter(){
        return new JwtRequestFilter();
    }

    @Bean
    public JwtService jwtService(){
        return new JwtServiceImpl();
    }
}
