package ar.com.alianza.controller;

import ar.com.alianza.contract.request.CreateSatelliteRequest;
import ar.com.alianza.entity.Satellite;
import ar.com.alianza.service.SatelliteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "rebel/api")
public class SatelliteController {

    private final SatelliteService satelliteService;

    Logger logger = LoggerFactory.getLogger(SatelliteController.class);

    public SatelliteController(SatelliteService satelliteService) {
        this.satelliteService = satelliteService;
    }

    @GetMapping("/satellite")
    public List<Satellite> findSatellites() {

        return satelliteService.findAllSatellites();
    }

    @GetMapping("/satellite/{name}")
    public Satellite findSatelliteByName(@PathVariable String name) {

        return satelliteService.findSatelliteByName(name);

    }

    @PostMapping("/satellite")
    @ResponseStatus(HttpStatus.CREATED)
    public Satellite createSatellite(@RequestBody @Valid CreateSatelliteRequest satellite) {

        logger.trace("Starting process to create satellite: " + satellite.getName());
        return satelliteService.createSatellite(Satellite.builder()
                .name(satellite.getName())
                .x(satellite.getPosition().getX())
                .y(satellite.getPosition().getY())
                .build());
    }

    @PutMapping("/satellite")
    public Satellite updateSatellite(@RequestBody @Valid CreateSatelliteRequest satellite) {

        logger.trace("Starting process to update satellite: " + satellite.getName());
        return satelliteService.updateSatellite(Satellite.builder()
                .name(satellite.getName())
                .x(satellite.getPosition().getX())
                .y(satellite.getPosition().getY())
                .build());

    }

    @DeleteMapping("/satellite/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSatellite(@PathVariable String name) {
        logger.trace("Starting process to delete satellite: " + name);
        satelliteService.deleteSatellite(name);

    }

}
