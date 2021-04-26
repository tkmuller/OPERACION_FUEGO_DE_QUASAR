package ar.com.alianza.controllers;

import ar.com.alianza.contracts.request.CreateSatelliteRequest;
import ar.com.alianza.entity.Satellite;
import ar.com.alianza.service.SatelliteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "rebel/api")
public class SatelliteController {

    private final SatelliteService satelliteService;

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
    public void createSatellite(@RequestBody @Valid CreateSatelliteRequest satellite) {


        satelliteService.createSatellite(Satellite.builder()
                .name(satellite.getName())
                .x(satellite.getPosition().getX())
                .y(satellite.getPosition().getY())
                .build());
    }

    @PutMapping("/satellite")
    public void updateSatellite(@RequestBody @Valid CreateSatelliteRequest satellite) {

        satelliteService.updateSatellite(Satellite.builder()
                .name(satellite.getName())
                .x(satellite.getPosition().getX())
                .y(satellite.getPosition().getY())
                .build());

    }

    @DeleteMapping("/satellite/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSatellite(@PathVariable String name) {
        satelliteService.deleteSatellite(name);

    }

}
