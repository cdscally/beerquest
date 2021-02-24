package com.scally.beerquest.service;

import com.scally.beerquest.mapper.PubMapper;
import com.scally.beerquest.model.Pub;
import com.scally.beerquest.model.PubDAO;
import com.scally.beerquest.repository.PubRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class BeerQuestService {

    @Autowired
    private PubMapper mapper;

    @Autowired
    private PubRepository repository;

    public List<Pub> getAllPubs(){
        return mapper.toPubList(repository.findAll());
    }

    public List<Pub> getClosestPubs(final Double latitude, final Double longitude, final int limit){
        return sortPubsByProximity(getAllPubs(), latitude, longitude, limit);
    }

    public List<Pub> getPubsByBeerRating(final double minScore, final int limit){
        //TODO handle optionals
        List<PubDAO> pubDaoList = repository.findAllByStarsBeerGreaterThanEqual(minScore).orElseThrow();
        List<Pub> pubs = mapper.toPubList(pubDaoList);

        return pubs.stream().sorted(Comparator.comparingDouble(Pub::getStarsBeer).reversed())
                .limit(limit).collect(Collectors.toList());
    }

    public List<Pub> getPubsByAtmosphereRating(final double minScore, final int limit){
        //TODO handle optionals
        List<PubDAO> pubDaoList = repository.findAllByStarsAtmosphereGreaterThanEqual(minScore).orElseThrow();
        List<Pub> pubs = mapper.toPubList(pubDaoList);

        return pubs.stream().sorted(Comparator.comparingDouble(Pub::getStarsAtmosphere).reversed())
                .limit(limit).collect(Collectors.toList());
    }

    public List<Pub> getPubsByAmenitiesRating(final double minScore, final int limit){
        //TODO handle optionals
        List<PubDAO> pubDaoList = repository.findAllByStarsAmenitiesGreaterThanEqual(minScore).orElseThrow();
        List<Pub> pubs = mapper.toPubList(pubDaoList);

        return pubs.stream().sorted(Comparator.comparingDouble(Pub::getStarsAmenities).reversed())
                .limit(limit).collect(Collectors.toList());
    }

    public List<Pub> getPubsByValueRating(final double minScore, final int limit){
        //TODO handle optionals
        List<PubDAO> pubDaoList = repository.findAllByStarsValueGreaterThanEqual(minScore).orElseThrow();
        List<Pub> pubs = mapper.toPubList(pubDaoList);

        return pubs.stream().sorted(Comparator.comparingDouble(Pub::getStarsValue).reversed())
                .limit(limit).collect(Collectors.toList());
    }

    public List<Pub> getTaggedPubs(final String tag) {

        return getAllPubs().stream()
                .filter(pub -> pub.getTags().contains(tag))
                .collect(Collectors.toList());

    }

    private List<Pub> sortPubsByProximity(final List<Pub> pubs, final double latitude, final double longitude, final int limit) {
        return pubs.stream()
                .sorted(
                        Comparator.comparingDouble(
                                pub -> {
                                    double latDiff = Math.abs(pub.getLatitude() - latitude);
                                    double longDiff = Math.abs(pub.getLongitude() - longitude);
                                    return Math.hypot(latDiff, longDiff);
                                }))
                .limit(limit)
                .collect(Collectors.toList());
    }


}
