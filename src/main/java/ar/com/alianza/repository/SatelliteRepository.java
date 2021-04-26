package ar.com.alianza.repository;

import ar.com.alianza.entity.Satellite;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SatelliteRepository extends CrudRepository<Satellite, String> {

    Optional<Satellite> findByName(String name);

}
