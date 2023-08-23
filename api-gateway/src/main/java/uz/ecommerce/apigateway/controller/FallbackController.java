package uz.ecommerce.apigateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.ecommerce.commons.model.response.ErrorResponse;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestController
public class FallbackController {

    @GetMapping("/authentication-service-fallback")
    public ResponseEntity<ErrorResponse> authenticationServiceFallback() {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message("Authentication service is down")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build();

        return new ResponseEntity<>(
                errorResponse, errorResponse.getStatus()
        );
    }

    @GetMapping("/file-service-fallback")
    public ResponseEntity<ErrorResponse> fileServiceFallback() {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message("File service is down")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build();

        return new ResponseEntity<>(
                errorResponse, errorResponse.getStatus()
        );
    }

    @GetMapping("/order-service-fallback")
    public ResponseEntity<ErrorResponse> orderServiceFallback() {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message("Order service is down")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build();

        return new ResponseEntity<>(
                errorResponse, errorResponse.getStatus()
        );
    }

    @GetMapping("/product-service-fallback")
    public ResponseEntity<ErrorResponse> productServiceFallback() {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message("Product service is down")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build();

        return new ResponseEntity<>(
                errorResponse, errorResponse.getStatus()
        );
    }

    @GetMapping("/user-service-fallback")
    public ResponseEntity<ErrorResponse> userServiceFallback() {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message("User service is down")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build();

        return new ResponseEntity<>(
                errorResponse, errorResponse.getStatus()
        );
    }
}
