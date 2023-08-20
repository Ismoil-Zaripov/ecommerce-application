package uz.ecommerce.commons.exception;

import lombok.Getter;

@Getter
public class APIException extends RuntimeException {
    private final int status;
    public APIException(String message, int status) {
        super(message);
        this.status = status;
    }
}
