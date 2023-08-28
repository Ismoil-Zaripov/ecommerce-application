package uz.ecommerce.apigateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;
import uz.ecommerce.commons.exception.APIException;
import uz.ecommerce.commons.model.response.ErrorResponse;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class ReactiveExceptionHandler {
    @ExceptionHandler({APIException.class})
    public Mono<ResponseEntity<ErrorResponse>> apiGatewayException(APIException e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.valueOf(e.getStatus()))
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build();

        ResponseEntity<ErrorResponse> response = new ResponseEntity<>(
                errorResponse,
                errorResponse.getStatus()
        );

        return Mono.just(response);
    }
}
