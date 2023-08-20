package uz.ecommerce.commons.config;

import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uz.ecommerce.commons.decoder.APIErrorDecoder;

@Configuration
public class FeignClientConfig {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {};
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new APIErrorDecoder();
    }
}
