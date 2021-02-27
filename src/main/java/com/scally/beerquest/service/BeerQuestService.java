package com.scally.beerquest.service;

import com.scally.beerquest.mapper.PubMapper;
import com.scally.beerquest.model.Pub;
import com.scally.beerquest.model.RatingCriteria;
import com.scally.beerquest.repository.PubRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
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

    public List<Pub> getPubsByRating(final RatingCriteria criteria, final double minScore, final int limit){

        switch (criteria) {
            case beer:
                List<Pub> beerPubs = mapper.toPubList(repository.findAllByStarsBeerGreaterThanEqual(minScore, Sort.by(Sort.Direction.DESC, "starsBeer")).get());
                return beerPubs.stream().limit(limit).collect(Collectors.toList());
            case atmosphere:
                List<Pub> atmospherePubs = mapper.toPubList(repository.findAllByStarsAtmosphereGreaterThanEqual(minScore, Sort.by(Sort.Direction.DESC, "starsAtmosphere")).get());
                return atmospherePubs.stream().limit(limit).collect(Collectors.toList());
            case amenities:
                List<Pub> amenitiesPubs = mapper.toPubList(repository.findAllByStarsAmenitiesGreaterThanEqual(minScore, Sort.by(Sort.Direction.DESC, "starsAmenities")).get());
                return amenitiesPubs.stream().limit(limit).collect(Collectors.toList());
            case value:
                List<Pub> valuePubs = mapper.toPubList(repository.findAllByStarsValueGreaterThanEqual(minScore, Sort.by(Sort.Direction.DESC, "starsValue")).get());
                return valuePubs.stream().limit(limit).collect(Collectors.toList());
            default:
                return new ArrayList<>();
        }
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
