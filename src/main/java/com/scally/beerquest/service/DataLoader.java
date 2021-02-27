package com.scally.beerquest.service;

import com.scally.beerquest.client.BeerQuestClient;
import com.scally.beerquest.mapper.PubMapper;
import com.scally.beerquest.model.Pub;
import com.scally.beerquest.model.PubDAO;
import com.scally.beerquest.repository.PubRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@Profile("!test")
public class DataLoader {

    @Autowired
    private BeerQuestClient client;

    @Autowired
    private PubMapper mapper;

    @Autowired
    private PubRepository repository;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {

        log.info("Loading data on startup");

        List<Pub> pubs = client.loadPubData();
        List<PubDAO> pubDaoList = mapper.toPubDaoList(pubs);
        repository.saveAll(pubDaoList);
    }

}
