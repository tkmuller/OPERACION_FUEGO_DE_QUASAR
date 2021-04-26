package ar.com.alianza.contracts.response;

import ar.com.alianza.contracts.request.Position;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DecodedMessage {

    private final Position position;
    private final String message;
}
