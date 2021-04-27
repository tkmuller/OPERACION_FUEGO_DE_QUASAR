package ar.com.alianza.service;

import ar.com.alianza.contract.request.IncomingMessage;
import ar.com.alianza.contract.request.Satellites;
import ar.com.alianza.contract.response.DecodedMessage;

import java.util.List;

public interface IntelligenceService {

    DecodedMessage decodeMessage(List<Satellites> satellites);

    boolean addEncodedMessage(String satelliteName, IncomingMessage incomingMessage);

    DecodedMessage getDecodeMessage();


}
