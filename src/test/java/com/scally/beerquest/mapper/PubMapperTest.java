package com.scally.beerquest.mapper;

import com.scally.beerquest.model.Pub;
import com.scally.beerquest.model.PubDAO;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PubMapperTest {

    private PubMapper underTest = new PubMapper();

    @Test
    void toPubShouldMapPubDaoToPub(){
        EasyRandom generator = new EasyRandom();
        PubDAO pubDAO = generator.nextObject(PubDAO.class);

        Pub pub = underTest.toPub(pubDAO);

        assertEquals(pub.getId(), pubDAO.getId());
        assertEquals(pub.getName(), pubDAO.getName());
        assertEquals(pub.getAddress(), pubDAO.getAddress());
        assertEquals(pub.getCategory(), pubDAO.getCategory());
        assertEquals(pub.getDate(), pubDAO.getDate());
        assertEquals(pub.getLatitude(), pubDAO.getLatitude());
        assertEquals(pub.getLongitude(), pubDAO.getLongitude());
        assertEquals(pub.getExceprt(), pubDAO.getExceprt());
        assertEquals(pub.getPhone(), pubDAO.getPhone());
        assertEquals(pub.getStarsAmenities(), pubDAO.getStarsAmenities());
        assertEquals(pub.getStarsBeer(), pubDAO.getStarsBeer());
        assertEquals(pub.getStarsAtmosphere(), pubDAO.getStarsAtmosphere());
        assertEquals(pub.getStarsValue(), pubDAO.getStarsValue());
        assertEquals(pub.getThumbnail(), pubDAO.getThumbnail());
        assertEquals(pub.getTwitter(), pubDAO.getTwitter());

    }

    @Test
    void toPubListShouldMapPubDaoListToPubList(){
        int listSize = 5;
        EasyRandom generator = new EasyRandom();
        List<PubDAO> pubDaoList = generator.objects(PubDAO.class, listSize)
                .collect(Collectors.toList());

        List<Pub> pubs = underTest.toPubList(pubDaoList);

        assertEquals(pubs.size(), listSize);
        assertEquals(pubs.get(0).getName(), pubDaoList.get(0).getName());
        assertEquals(pubs.get(1).getName(), pubDaoList.get(1).getName());
        assertEquals(pubs.get(2).getName(), pubDaoList.get(2).getName());
        assertEquals(pubs.get(3).getName(), pubDaoList.get(3).getName());
        assertEquals(pubs.get(4).getName(), pubDaoList.get(4).getName());

    }

    @Test
    void toPubDaoShouldMapPubToPubDao(){
        EasyRandom generator = new EasyRandom();
        Pub pub = generator.nextObject(Pub.class);

        PubDAO pubDao = underTest.toPubDao(pub);

        assertEquals(pub.getId(), pubDao.getId());
        assertEquals(pub.getName(), pubDao.getName());
        assertEquals(pub.getAddress(), pubDao.getAddress());
        assertEquals(pub.getCategory(), pubDao.getCategory());
        assertEquals(pub.getDate(), pubDao.getDate());
        assertEquals(pub.getLatitude(), pubDao.getLatitude());
        assertEquals(pub.getLongitude(), pubDao.getLongitude());
        assertEquals(pub.getExceprt(), pubDao.getExceprt());
        assertEquals(pub.getPhone(), pubDao.getPhone());
        assertEquals(pub.getStarsAmenities(), pubDao.getStarsAmenities());
        assertEquals(pub.getStarsBeer(), pubDao.getStarsBeer());
        assertEquals(pub.getStarsAtmosphere(), pubDao.getStarsAtmosphere());
        assertEquals(pub.getStarsValue(), pubDao.getStarsValue());
        assertEquals(pub.getThumbnail(), pubDao.getThumbnail());
        assertEquals(pub.getTwitter(), pubDao.getTwitter());

    }

    @Test
    void toPubDaoListShouldMapPubListToPubDaoList(){
        int listSize = 5;
        EasyRandom generator = new EasyRandom();
        List<Pub> pubList = generator.objects(Pub.class, listSize)
                .collect(Collectors.toList());

        List<PubDAO> pubDaoList = underTest.toPubDaoList(pubList);

        assertEquals(pubDaoList.size(), listSize);
        assertEquals(pubDaoList.get(0).getName(), pubList.get(0).getName());
        assertEquals(pubDaoList.get(1).getName(), pubList.get(1).getName());
        assertEquals(pubDaoList.get(2).getName(), pubList.get(2).getName());
        assertEquals(pubDaoList.get(3).getName(), pubList.get(3).getName());
        assertEquals(pubDaoList.get(4).getName(), pubList.get(4).getName());

    }
}
