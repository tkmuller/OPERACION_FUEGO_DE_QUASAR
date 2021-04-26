package ar.com.alianza.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SatelliteAlreadyExistException extends RuntimeException {


    private static final String MESSAGE = "The Satellite %s Already Exist";

    public SatelliteAlreadyExistException(String name) {
        super(String.format(MESSAGE, name));
    }
}
