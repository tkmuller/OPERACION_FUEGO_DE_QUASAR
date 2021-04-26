package ar.com.alianza.contracts.request;

import ar.com.alianza.validator.MinSizeConstraint;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
public class IncomingMessages {

    @NotEmpty(message = "Satellites info is mandatory, must contain at least 2 satellite.")
    @MinSizeConstraint
    private List<@Valid Satellites> satellites;

}
