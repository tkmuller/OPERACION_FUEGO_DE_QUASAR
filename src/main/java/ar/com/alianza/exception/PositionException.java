package ar.com.alianza.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PositionException extends RuntimeException {

    private static final String MESSAGE = "Position X and Y is Mandatory";

    public PositionException() {
        super(MESSAGE);
    }
}
