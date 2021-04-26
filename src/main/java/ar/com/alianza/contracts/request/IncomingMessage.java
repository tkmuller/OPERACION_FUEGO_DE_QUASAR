package ar.com.alianza.contracts.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Builder
public class IncomingMessage {

    @NotNull(message = " Distance is mandatory")
    @Min(value = 0, message = " Distance should not be less than 0")
    private Double distance;

    @NotNull(message = " Message is mandatory")
    private String[] message;

    public IncomingMessage(@NotNull(message = " Distance is mandatory") @Min(value = 0, message = " Distance should not be less than 0") Double distance,
                           @NotNull(message = " Message is mandatory") String[] message) {

        this.distance = distance;
        this.message = message;
    }

    public IncomingMessage() {
    }
}
