package ar.com.alianza.service.impl;

import ar.com.alianza.entity.Satellite;
import ar.com.alianza.exception.SatelliteAlreadyExistException;
import ar.com.alianza.exception.SatelliteNotFoundException;
import ar.com.alianza.repository.SatelliteRepository;
import ar.com.alianza.service.SatelliteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SatelliteServiceImpl implements SatelliteService {

    private final SatelliteRepository satelliteRepository;

    Logger logger = LoggerFactory.getLogger(SatelliteServiceImpl.class);

    public SatelliteServiceImpl(SatelliteRepository satelliteRepository) {
        this.satelliteRepository = satelliteRepository;
    }

    @Override
    public Satellite createSatellite(Satellite satellite) {

        satelliteRepository.findByName(satellite.getName()).ifPresentOrElse(
                (satelliteSaved)
                        -> {
                    logger.error("Satellite " + satellite.getName() + " already Exist");
                    throw new SatelliteAlreadyExistException(satelliteSaved.getName());
                },
                ()
                        -> satelliteRepository.save(satellite));
        logger.trace("satellite " + satellite.getName() + " created");
        return findSatelliteByName(satellite.getName());

    }

    @Override
    public List<Satellite> findAllSatellites() {

        List<Satellite> satellites = new ArrayList<>();
        satelliteRepository.findAll().forEach(satellites::add);
        return satellites;


    }

    @Override
    public Satellite findSatelliteByName(String satelliteName) {

        return satelliteRepository.findByName(satelliteName)
                .orElseThrow(() -> new SatelliteNotFoundException(satelliteName));

    }

    @Override
    public Satellite updateSatellite(Satellite satellite) {
        satelliteRepository.findByName(satellite.getName()).ifPresentOrElse(
                (satelliteSaved)
                        -> {
                    satelliteSaved.setX(satellite.getX());
                    satelliteSaved.setY(satellite.getY());
                    satelliteRepository.save(satelliteSaved);
                },
                ()
                        -> {
                    logger.error("Satellite " + satellite.getName() + " not found");
                    throw new SatelliteNotFoundException(satellite.getName());
                });
        logger.trace("satellite " + satellite.getName() + " Updated");
        return findSatelliteByName(satellite.getName());


    }

    @Override
    public void deleteSatellite(String satelliteName) {
        Satellite satellite = findSatelliteByName(satelliteName);
        satelliteRepository.delete(satellite);
        logger.trace("satellite " + satellite.getName() + " Eliminated");
    }
}
