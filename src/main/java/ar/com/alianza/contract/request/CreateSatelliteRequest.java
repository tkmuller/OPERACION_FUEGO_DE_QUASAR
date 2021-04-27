package ar.com.alianza.contract.request;


import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public class CreateSatelliteRequest {

    @NotEmpty(message = "Satellites name is mandatory")
    private String name;
    @NotNull(message = "Satellites position is mandatory")
    private @Valid Position position;
}
