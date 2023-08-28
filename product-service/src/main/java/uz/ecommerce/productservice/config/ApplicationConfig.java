package uz.ecommerce.productservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uz.ecommerce.commons.exception.APIExceptionHandler;

@Configuration
public class ApplicationConfig {
    @Bean
    public ResponseEntityExceptionHandler apiExceptionHandler(){
        return new APIExceptionHandler();
    }
}
