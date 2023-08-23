package uz.ecommerce.apigateway.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import reactor.core.publisher.Mono;
import uz.ecommerce.authenticationservice.filter.JwtRequestFilter;
import uz.ecommerce.commons.exception.APIExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
public class ApplicationConfig {

    @Bean
    public ResponseEntityExceptionHandler apiExceptionHandler(){
        return new APIExceptionHandler();
    }

    @Bean
    @Lazy(false)
    public List<GroupedOpenApi> apis(RouteDefinitionLocator locator) {
        List<GroupedOpenApi> groups = new ArrayList<>();
        List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
        for (RouteDefinition definition : definitions) {
            log.info("id: " + definition.getId() + "  " + definition.getUri().toString());
        }
        definitions.stream().filter(routeDefinition -> routeDefinition.getId().matches(".*-SERVICE")).forEach(routeDefinition -> {
            String name = routeDefinition.getId().replaceAll("-SERVICE", "");
            GroupedOpenApi.builder().pathsToMatch("/" + name + "/**").group(name).build();
        });
        return groups;
    }

    @Bean
    public OpenAPI gateWayOpenApi() {
        return new OpenAPI().info(new Info().title("Demo Application Microservices APIs ")
                .description("Documentation for all the Microservices in Demo Application")
                .version("v1.0.0")
                .contact(new Contact()
                        .name("Demo Application Development Team")
                        .email("demo_support@imaginarycompany.com")));
    }

    @Bean
    public JwtRequestFilter requestFilter(){
        return new JwtRequestFilter();
    }

}
