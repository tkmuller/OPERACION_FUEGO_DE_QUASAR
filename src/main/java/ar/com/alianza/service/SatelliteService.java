package ar.com.alianza.service;

import ar.com.alianza.entity.Satellite;
import ar.com.alianza.exception.SatelliteAlreadyExistException;
import ar.com.alianza.exception.SatelliteNotFoundException;
import ar.com.alianza.repository.SatelliteRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SatelliteService {

    private final SatelliteRepository satelliteRepository;

    public SatelliteService(SatelliteRepository satelliteRepository) {
        this.satelliteRepository = satelliteRepository;
    }

    public void createSatellite(Satellite satellite) {

        satelliteRepository.findByName(satellite.getName()).ifPresentOrElse(
                (satelliteSaved)
                        -> {
                    throw new SatelliteAlreadyExistException(satelliteSaved.getName());
                },
                ()
                        -> satelliteRepository.save(satellite));

    }

    public List<Satellite> findAllSatellites() {

        List<Satellite> satellites = new ArrayList<>();
        satelliteRepository.findAll().forEach(satellites::add);
        return satellites;


    }

    public Satellite findSatelliteByName(String satelliteName) {

        return satelliteRepository.findByName(satelliteName)
                .orElseThrow(() -> new SatelliteNotFoundException(satelliteName));

    }

    public void updateSatellite(Satellite satellite) {
        satelliteRepository.findByName(satellite.getName()).ifPresentOrElse(
                (satelliteSaved)
                        -> {
                    satelliteSaved.setX(satellite.getX());
                    satelliteSaved.setY(satellite.getY());
                    satelliteRepository.save(satelliteSaved);
                },
                ()
                        -> {
                    throw new SatelliteNotFoundException(satellite.getName());
                });
    }

    public void deleteSatellite(String satelliteName) {
        Satellite satellite = findSatelliteByName(satelliteName);
        satelliteRepository.delete(satellite);
    }
}
