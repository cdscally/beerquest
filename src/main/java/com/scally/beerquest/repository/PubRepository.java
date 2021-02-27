package com.scally.beerquest.repository;

import com.scally.beerquest.model.PubDAO;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PubRepository extends CrudRepository<PubDAO, UUID> {

    Optional<List<PubDAO>> findAllByStarsAmenitiesGreaterThanEqual(Double threshold, Sort sort);
    Optional<List<PubDAO>> findAllByStarsBeerGreaterThanEqual(Double threshold, Sort sort);
    Optional<List<PubDAO>> findAllByStarsValueGreaterThanEqual(Double threshold, Sort sort);
    Optional<List<PubDAO>> findAllByStarsAtmosphereGreaterThanEqual(Double threshold, Sort sort);

    List<PubDAO> findAll();

}

