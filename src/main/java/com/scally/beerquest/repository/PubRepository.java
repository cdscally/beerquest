package com.scally.beerquest.repository;

import com.scally.beerquest.model.PubDAO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PubRepository extends CrudRepository<PubDAO, UUID> {

    Optional<List<PubDAO>> findAllByStarsAmenitiesGreaterThanEqual(Double threshold);
    Optional<List<PubDAO>> findAllByStarsBeerGreaterThanEqual(Double threshold);
    Optional<List<PubDAO>> findAllByStarsValueGreaterThanEqual(Double threshold);
    Optional<List<PubDAO>> findAllByStarsAtmosphereGreaterThanEqual(Double threshold);

    List<PubDAO> findAll();

}

