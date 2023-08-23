package uz.ecommerce.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uz.ecommerce.authenticationservice.filter.JwtRequestFilter;
import uz.ecommerce.commons.exception.APIExceptionHandler;

@Configuration
public class ApplicationConfig {
    @Bean
    public ResponseEntityExceptionHandler apiExceptionHandler(){
        return new APIExceptionHandler();
    }
    @Bean
    public JwtRequestFilter requestFilter(){
        return new JwtRequestFilter();
    }
}
