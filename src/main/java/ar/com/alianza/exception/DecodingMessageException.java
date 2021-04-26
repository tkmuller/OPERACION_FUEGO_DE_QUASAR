package ar.com.alianza.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DecodingMessageException extends RuntimeException {

    private static final String MESSAGE = "You need at least ( 3 ) signal, you only have ( %s ) ";

    public DecodingMessageException(int signals) {
        super(String.format(MESSAGE,signals));
    }


}
