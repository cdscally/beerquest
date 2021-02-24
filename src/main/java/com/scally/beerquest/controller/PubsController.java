package com.scally.beerquest.controller;

import com.scally.beerquest.model.Pub;
import com.scally.beerquest.model.RatingCriteria;
import com.scally.beerquest.service.BeerQuestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/pubs")
public class PubsController {

    private final BeerQuestService service;

    public PubsController(final BeerQuestService service){
        this.service = service;
    }

    @RequestMapping(produces = "application/json", method = RequestMethod.GET)
    public List<Pub> getAllPubs() {

        return service.getAllPubs();

    }

    @RequestMapping(value = "/closest", produces = "application/json", method = RequestMethod.GET)
    public List<Pub> getClosestPubs(@RequestParam (name = "limit", defaultValue = "1") int limit,
                                    @RequestParam (name = "lat") double latitude,
                                    @RequestParam (name = "long") double longitude) {

        return service.getClosestPubs(latitude, longitude, limit);

    }

    @RequestMapping(value = "/top", produces = "application/json", method = RequestMethod.GET)
    public List<Pub> getTopPubsByMinimumScore(@RequestParam (name = "criteria") String criteria,
                                              @RequestParam (name = "minimum-score") double minScore,
                                              @RequestParam (name = "limit", defaultValue = "1") int limit) {

        RatingCriteria ratingCriteria = RatingCriteria.fromQueryParam(criteria).orElseThrow();

        switch (ratingCriteria) {
            case BEER: {
                return service.getPubsByBeerRating(minScore, limit);
            }
            case ATMOSPHERE: {
                return service.getPubsByAtmosphereRating(minScore, limit);
            }
            case AMENITIES: {
                return service.getPubsByAmenitiesRating(minScore, limit);
            }
            case VALUE: {
                return service.getPubsByValueRating(minScore, limit);
            }
            default:
                //TODO
                return null;
        }
    }

    @RequestMapping(value = "/tag", produces = "application/json", method = RequestMethod.GET)
    public List<Pub> getPubsByTag(@RequestParam (name = "tag") String tag) {

        return service.getTaggedPubs(tag);

    }

}
