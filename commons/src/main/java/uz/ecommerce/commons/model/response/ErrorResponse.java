package uz.ecommerce.commons.model.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
@Builder
public class ErrorResponse {
    private String message;
    private HttpStatus status;
    private ZonedDateTime timestamp;
}
