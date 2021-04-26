package ar.com.alianza.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SatelliteNotFoundException extends RuntimeException {

    private static final String MESSAGE = "The Satellite %s not found";

    public SatelliteNotFoundException(String name) {
        super(String.format(MESSAGE, name));
    }
}
