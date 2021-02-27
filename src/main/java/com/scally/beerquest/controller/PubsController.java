package com.scally.beerquest.controller;

import com.scally.beerquest.model.Pub;
import com.scally.beerquest.model.RatingCriteria;
import com.scally.beerquest.service.BeerQuestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/pubs")
@Slf4j
public class PubsController {

    private final BeerQuestService service;

    public PubsController(final BeerQuestService service){
        this.service = service;
    }

    @RequestMapping(produces = "application/json", method = RequestMethod.GET)
    public List<Pub> getAllPubs() {

        log.info("Request received to retrieve all pubs in dataset");

        return service.getAllPubs();

    }

    @RequestMapping(value = "/closest", produces = "application/json", method = RequestMethod.GET)
    public List<Pub> getClosestPubs(@RequestParam (name = "limit", defaultValue = "1") int limit,
                                    @RequestParam (name = "lat") double latitude,
                                    @RequestParam (name = "long") double longitude) {

        log.info("Request received to retrieve {} closest pubs to location({}, {})", limit, latitude, longitude);

        return service.getClosestPubs(latitude, longitude, limit);

    }

    @RequestMapping(value = "/top", produces = "application/json", method = RequestMethod.GET)
    public List<Pub> getTopPubsByMinimumScore(@RequestParam (name = "criteria") RatingCriteria criteria,
                                              @RequestParam (name = "minimum-score") double minScore,
                                              @RequestParam (name = "limit", defaultValue = "1") int limit) {

        log.info("Request received to retrieve top {} pubs with {} rating greater than {}", limit, criteria, minScore);

        return service.getPubsByRating(criteria, minScore, limit);
    }

    @RequestMapping(value = "/tag", produces = "application/json", method = RequestMethod.GET)
    public List<Pub> getPubsByTag(@RequestParam (name = "tag") String tag) {

        log.info("Request received to retrieve all pubs with tag {}", tag);

        return service.getTaggedPubs(tag);

    }

}
