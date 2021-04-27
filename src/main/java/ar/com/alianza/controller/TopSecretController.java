package ar.com.alianza.controller;

import ar.com.alianza.contract.request.IncomingMessage;
import ar.com.alianza.contract.request.IncomingMessages;
import ar.com.alianza.contract.response.DecodedMessage;
import ar.com.alianza.service.IntelligenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "rebel/api")
public class TopSecretController {

    private final IntelligenceService intelligenceService;

    Logger logger = LoggerFactory.getLogger(TopSecretController.class);

    public TopSecretController(IntelligenceService intelligenceService) {
        this.intelligenceService = intelligenceService;
    }

    @PostMapping("/topsecret")
    public DecodedMessage decodeTopSecretMessages(@RequestBody @Valid IncomingMessages incomingMessages) {

        logger.trace("Starting True-range multilateration process");
        return intelligenceService.decodeMessage(incomingMessages.getSatellites());
    }

    @GetMapping("/topsecret_split")
    public DecodedMessage getDecodeMessage() {
        logger.trace("Starting True-range multilateration process");
        return intelligenceService.getDecodeMessage();
    }

    @PostMapping("/topsecret_split/{satelliteName}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addEncodedMessage(@PathVariable String satelliteName,
                                  @RequestBody @Valid IncomingMessage incomingMessage) {

        logger.trace("Starting process to save a signal: " + satelliteName);
        intelligenceService.addEncodedMessage(satelliteName, incomingMessage);
    }

}
