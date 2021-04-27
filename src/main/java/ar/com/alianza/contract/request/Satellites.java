package ar.com.alianza.contract.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Builder
public class Satellites {

    @NotNull(message = " Name is mandatory")
    private String name;

    @NotNull(message = " Distance is mandatory")
    @Min(value = 0, message = " Distance should not be less than 0")
    private Double distance;

    @NotNull(message = " Message is mandatory")
    private String[] message;

    public Satellites(String name, Double distance, String[] message) {
        this.name = name;
        this.distance = distance;
        this.message = message;
    }

    public Satellites() {
    }
}
