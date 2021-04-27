package ar.com.alianza.service;

import ar.com.alianza.entity.Satellite;

import java.util.List;

public interface SatelliteService {

    Satellite createSatellite(Satellite satellite);

    List<Satellite> findAllSatellites();

    Satellite findSatelliteByName(String satelliteName);

    Satellite updateSatellite(Satellite satellite);

    void deleteSatellite(String satelliteName);

}
