package ar.com.alianza.service;

import ar.com.alianza.entity.Satellite;
import ar.com.alianza.exception.SatelliteAlreadyExistException;
import ar.com.alianza.exception.SatelliteNotFoundException;
import ar.com.alianza.repository.SatelliteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SatelliteServiceTest {

    private SatelliteService satelliteService;

    @Mock
    private SatelliteRepository mockSatelliteRepository;

    @Captor
    private ArgumentCaptor<Satellite> captorSatellite;

    private final String SATELLITE_1_NAME = "MC85";
    private final Double SATELLITE_1_X = -500.0;
    private final Double SATELLITE_1_Y = -200.0;

    private final Double SATELLITE_2_X = 100.0;
    private final Double SATELLITE_2_Y = -100.0;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        satelliteService = new SatelliteService(mockSatelliteRepository);

    }

    @Test
    void createSatellite() {

        satelliteService.createSatellite(Satellite.builder().name(SATELLITE_1_NAME).x(SATELLITE_1_X).y(SATELLITE_1_Y).build());

        Mockito.verify(mockSatelliteRepository).save(captorSatellite.capture());

    }

    @Test
    void whenSatelliteAlreadyExist() {

        Satellite satellite = Satellite.builder().name(SATELLITE_1_NAME).x(SATELLITE_1_X).y(SATELLITE_1_Y).build();
        Mockito.when(mockSatelliteRepository.findByName(SATELLITE_1_NAME)).thenReturn(Optional.of(satellite));

        assertThrows(SatelliteAlreadyExistException.class, () -> satelliteService.createSatellite(satellite));

    }

    @Test
    void findSatelliteByName() {

        Satellite satellite = Satellite.builder().name(SATELLITE_1_NAME).x(SATELLITE_1_X).y(SATELLITE_1_Y).build();
        Mockito.when(mockSatelliteRepository.findByName(SATELLITE_1_NAME)).thenReturn(Optional.of(satellite));

        Satellite result = satelliteService.findSatelliteByName(SATELLITE_1_NAME);
        assertEquals(result, satellite);
    }

    @Test
    void whenSatelliteDontExist() {

        assertThrows(SatelliteNotFoundException.class, () -> satelliteService.findSatelliteByName(SATELLITE_1_NAME));
    }

    @Test
    void updateSatellite() {

        Satellite satellite = Satellite.builder().name(SATELLITE_1_NAME).x(SATELLITE_1_X).y(SATELLITE_1_Y).build();
        Mockito.when(mockSatelliteRepository.findByName(SATELLITE_1_NAME)).thenReturn(Optional.of(satellite));

        satelliteService.updateSatellite(Satellite.builder().name(SATELLITE_1_NAME).x(SATELLITE_2_X).y(SATELLITE_2_Y).build());

        Mockito.verify(mockSatelliteRepository).save(captorSatellite.capture());
    }

    @Test
    void whenSatelliteNotFoundToUpdate() {

        Satellite satellite = Satellite.builder().name(SATELLITE_1_NAME).x(SATELLITE_1_X).y(SATELLITE_1_Y).build();

        assertThrows(SatelliteNotFoundException.class, () -> satelliteService.updateSatellite(satellite));

    }

    @Test
    void deleteSatellite() {

        Satellite satellite = Satellite.builder().name(SATELLITE_1_NAME).x(SATELLITE_1_X).y(SATELLITE_1_Y).build();
        Mockito.when(mockSatelliteRepository.findByName(SATELLITE_1_NAME)).thenReturn(Optional.of(satellite)).thenReturn(null);

        satelliteService.deleteSatellite((SATELLITE_1_NAME));

        Mockito.verify(mockSatelliteRepository).delete(satellite);
    }
}