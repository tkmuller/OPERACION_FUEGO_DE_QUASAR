package ar.com.alianza.controllers;

import ar.com.alianza.contracts.request.IncomingMessage;
import ar.com.alianza.contracts.request.IncomingMessages;
import ar.com.alianza.contracts.response.DecodedMessage;
import ar.com.alianza.service.IntelligenceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "rebel/api")
public class TopSecretController {

    private final IntelligenceService intelligenceService;

    public TopSecretController(IntelligenceService intelligenceService) {
        this.intelligenceService = intelligenceService;
    }

    @PostMapping("/topsecret")
    public DecodedMessage decodeTopSecretMessages(@RequestBody @Valid IncomingMessages incomingMessages) {

        return intelligenceService.decodeMessage(incomingMessages.getSatellites());
    }

    @GetMapping("/topsecret_split")
    public DecodedMessage getDecodeMessage() {

        return intelligenceService.getDecodeMessage();
    }

    @PostMapping("/topsecret_split/{satelliteName}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addEncodedMessage(@PathVariable String satelliteName,
                                  @RequestBody @Valid IncomingMessage incomingMessage) {

        intelligenceService.addEncodedMessage(satelliteName, incomingMessage);


    }

}
