package ar.com.alianza.service;

import ar.com.alianza.contract.request.IncomingMessage;
import ar.com.alianza.contract.request.Satellites;
import ar.com.alianza.contract.response.DecodedMessage;
import ar.com.alianza.entity.Satellite;
import ar.com.alianza.exception.DecodingMessageException;
import ar.com.alianza.service.impl.IntelligenceServiceImpl;
import ar.com.alianza.service.impl.SatelliteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IntelligenceServiceImplTest {

    private IntelligenceServiceImpl intelligenceServiceImpl;

    @Mock
    private SatelliteServiceImpl satelliteServiceImpl;

    private final String SATELLITE_1_NAME = "MC85";
    private final Double SATELLITE_1_X = -500.0;
    private final Double SATELLITE_1_Y = -200.0;
    private final Double SATELLITE_1_DISTANCE = 100.0;
    private final String[] SATELLITE_1_MESSAGE = {"este", "", "", "mensaje", ""};

    private final String SATELLITE_2_NAME = "MC80";
    private final Double SATELLITE_2_X = 100.0;
    private final Double SATELLITE_2_Y = -100.0;
    private final Double SATELLITE_2_DISTANCE = 115.5;
    private final String[] SATELLITE_2_MESSAGE = {"", "es", "", "", "secreto"};

    private final String SATELLITE_3_NAME = "VAKBEOR";
    private final Double SATELLITE_3_X = 500.0;
    private final Double SATELLITE_3_Y = 100.0;
    private final Double SATELLITE_3_DISTANCE = 142.7;
    private final String[] SATELLITE_3_MESSAGE = {"este", "", "un", "", ""};

    private final String RESULT_MESSAGE = "este es un mensaje secreto";
    private final Double RESULT_X = -58.32;
    private final Double RESULT_Y = -69.55;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        intelligenceServiceImpl = new IntelligenceServiceImpl(satelliteServiceImpl);
    }

    @Test
    void decodeMessage() {

        List<Satellites> satellites = new ArrayList<>();
        satellites.add(Satellites.builder().name(SATELLITE_1_NAME).distance(SATELLITE_1_DISTANCE).message(SATELLITE_1_MESSAGE).build());
        satellites.add(Satellites.builder().name(SATELLITE_2_NAME).distance(SATELLITE_2_DISTANCE).message(SATELLITE_2_MESSAGE).build());
        satellites.add(Satellites.builder().name(SATELLITE_3_NAME).distance(SATELLITE_3_DISTANCE).message(SATELLITE_3_MESSAGE).build());

        Satellite satellite1 = Satellite.builder().x(SATELLITE_1_X).y(SATELLITE_1_Y).build();
        Mockito.when(satelliteServiceImpl.findSatelliteByName(SATELLITE_1_NAME)).thenReturn(satellite1);

        Satellite satellite2 = Satellite.builder().x(SATELLITE_2_X).y(SATELLITE_2_Y).build();
        Mockito.when(satelliteServiceImpl.findSatelliteByName(SATELLITE_2_NAME)).thenReturn(satellite2);

        Satellite satellite3 = Satellite.builder().x(SATELLITE_3_X).y(SATELLITE_3_Y).build();
        Mockito.when(satelliteServiceImpl.findSatelliteByName(SATELLITE_3_NAME)).thenReturn(satellite3);

        DecodedMessage result = intelligenceServiceImpl.decodeMessage(satellites);
        assertEquals(RESULT_MESSAGE, result.getMessage());
        assertEquals(RESULT_X, result.getPosition().getX());
        assertEquals(RESULT_Y, result.getPosition().getY());

    }

    @Test
    void addEncodedMessage() {

        IncomingMessage incomingMessage = IncomingMessage.builder().distance(SATELLITE_1_DISTANCE).message(SATELLITE_1_MESSAGE).build();

        Satellite satellite = Satellite.builder().x(SATELLITE_1_X).y(SATELLITE_1_Y).build();
        Mockito.when(satelliteServiceImpl.findSatelliteByName(SATELLITE_1_NAME)).thenReturn(satellite);

        boolean result = intelligenceServiceImpl.addEncodedMessage(SATELLITE_1_NAME, incomingMessage);
        assertTrue(result);

    }

    @Test
    void getDecodeMessage() {

        IncomingMessage incomingMessage1 = IncomingMessage.builder().distance(SATELLITE_1_DISTANCE).message(SATELLITE_1_MESSAGE).build();
        Satellite satellite1 = Satellite.builder().x(SATELLITE_1_X).y(SATELLITE_1_Y).build();
        Mockito.when(satelliteServiceImpl.findSatelliteByName(SATELLITE_1_NAME)).thenReturn(satellite1);
        intelligenceServiceImpl.addEncodedMessage(SATELLITE_1_NAME, incomingMessage1);

        IncomingMessage incomingMessage2 = IncomingMessage.builder().distance(SATELLITE_2_DISTANCE).message(SATELLITE_2_MESSAGE).build();
        Satellite satellite2 = Satellite.builder().x(SATELLITE_2_X).y(SATELLITE_2_Y).build();
        Mockito.when(satelliteServiceImpl.findSatelliteByName(SATELLITE_2_NAME)).thenReturn(satellite2);
        intelligenceServiceImpl.addEncodedMessage(SATELLITE_2_NAME, incomingMessage2);

        IncomingMessage incomingMessage3 = IncomingMessage.builder().distance(SATELLITE_3_DISTANCE).message(SATELLITE_3_MESSAGE).build();
        Satellite satellite3 = Satellite.builder().x(SATELLITE_3_X).y(SATELLITE_3_Y).build();
        Mockito.when(satelliteServiceImpl.findSatelliteByName(SATELLITE_3_NAME)).thenReturn(satellite3);
        intelligenceServiceImpl.addEncodedMessage(SATELLITE_3_NAME, incomingMessage3);

        DecodedMessage result = intelligenceServiceImpl.getDecodeMessage();
        assertEquals(RESULT_MESSAGE, result.getMessage());
        assertEquals(RESULT_X, result.getPosition().getX());
        assertEquals(RESULT_Y, result.getPosition().getY());
    }

    @Test
    void whenDontHaveEnoughSignals() {

        IncomingMessage incomingMessage1 = IncomingMessage.builder().distance(SATELLITE_1_DISTANCE).message(SATELLITE_1_MESSAGE).build();
        Satellite satellite1 = Satellite.builder().x(SATELLITE_1_X).y(SATELLITE_1_Y).build();
        Mockito.when(satelliteServiceImpl.findSatelliteByName(SATELLITE_1_NAME)).thenReturn(satellite1);
        intelligenceServiceImpl.addEncodedMessage(SATELLITE_1_NAME, incomingMessage1);

        assertThrows(DecodingMessageException.class, () -> intelligenceServiceImpl.getDecodeMessage());

    }

}