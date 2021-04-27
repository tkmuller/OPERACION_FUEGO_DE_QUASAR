package ar.com.alianza.contract.response;

import ar.com.alianza.contract.request.Position;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DecodedMessage {

    private final Position position;
    private final String message;
}
