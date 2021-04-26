package ar.com.alianza.contracts.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Builder
public class Position {

    @NotNull(message = " X is mandatory")
    @Min(value = -500, message = " X should not be less than -500")
    @Max(value = 500, message = " X should not be greater than 500")
    private Double x;
    @NotNull(message = " Y is mandatory")
    @Min(value = -500, message = "Y should not be less than -500")
    @Max(value = 500, message = "Y should not be greater than 500")
    private Double y;

    public Position(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Position() {


    }
}
