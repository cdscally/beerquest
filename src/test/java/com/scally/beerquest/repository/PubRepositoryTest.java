package com.scally.beerquest.repository;

import com.scally.beerquest.BeerquestApplication;
import com.scally.beerquest.model.PubDAO;
import org.jeasy.random.EasyRandom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BeerquestApplication.class)
@ActiveProfiles("test")
public class PubRepositoryTest {

    @Autowired
    private PubRepository underTest;

    @Test
    public void shouldReturnAllPubsWithAmenitiesScoreAboveAThreshold(){

        underTest.deleteAll();

        EasyRandom generator = new EasyRandom();

        PubDAO pubDAO1 = generator.nextObject(PubDAO.class);
        pubDAO1.setStarsAmenities(5.0);

        PubDAO pubDAO2 = generator.nextObject(PubDAO.class);
        pubDAO2.setStarsAmenities(2.0);

        List<PubDAO> pubDaoList = List.of(pubDAO1, pubDAO2);

        underTest.saveAll(pubDaoList);

        Optional<List<PubDAO>> queryResults = underTest.findAllByStarsAmenitiesGreaterThanEqual(4.0, Sort.by(Sort.Direction.DESC, "starsAmenities"));
        List<PubDAO> pubDAOS = queryResults.get();
        assertEquals(1, pubDAOS.size());
        assertEquals(pubDAOS.get(0), pubDAO1);

    }

    @Test
    public void shouldReturnAllPubsWithBeerScoreAboveAThreshold(){

        underTest.deleteAll();

        EasyRandom generator = new EasyRandom();

        PubDAO pubDAO1 = generator.nextObject(PubDAO.class);
        pubDAO1.setStarsBeer(5.0);

        PubDAO pubDAO2 = generator.nextObject(PubDAO.class);
        pubDAO2.setStarsBeer(2.0);

        List<PubDAO> pubDaoList = List.of(pubDAO1, pubDAO2);

        underTest.saveAll(pubDaoList);

        Optional<List<PubDAO>> queryResults = underTest.findAllByStarsBeerGreaterThanEqual(4.0, Sort.by(Sort.Direction.DESC, "starsBeer"));
        List<PubDAO> pubDAOS = queryResults.get();
        assertEquals(1, pubDAOS.size());
        assertEquals(pubDAOS.get(0), pubDAO1);

    }

    @Test
    public void shouldReturnAllPubsWithAtmosphereScoreAboveAThreshold(){

        underTest.deleteAll();

        EasyRandom generator = new EasyRandom();

        PubDAO pubDAO1 = generator.nextObject(PubDAO.class);
        pubDAO1.setStarsAtmosphere(5.0);

        PubDAO pubDAO2 = generator.nextObject(PubDAO.class);
        pubDAO2.setStarsAtmosphere(2.0);

        List<PubDAO> pubDaoList = List.of(pubDAO1, pubDAO2);

        underTest.saveAll(pubDaoList);

        Optional<List<PubDAO>> queryResults = underTest.findAllByStarsAtmosphereGreaterThanEqual(4.0, Sort.by(Sort.Direction.DESC, "starsAtmosphere"));
        List<PubDAO> pubDAOS = queryResults.get();
        assertEquals(1, pubDAOS.size());
        assertEquals(pubDAOS.get(0), pubDAO1);

    }

    @Test
    public void shouldReturnAllPubsWithValueScoreAboveAThreshold(){

        underTest.deleteAll();

        EasyRandom generator = new EasyRandom();

        PubDAO pubDAO1 = generator.nextObject(PubDAO.class);
        pubDAO1.setStarsValue(5.0);

        PubDAO pubDAO2 = generator.nextObject(PubDAO.class);
        pubDAO2.setStarsValue(2.0);

        List<PubDAO> pubDaoList = List.of(pubDAO1, pubDAO2);

        underTest.saveAll(pubDaoList);

        Optional<List<PubDAO>> queryResults = underTest.findAllByStarsValueGreaterThanEqual(4.0, Sort.by(Sort.Direction.DESC, "starsValue"));
        List<PubDAO> pubDAOS = queryResults.get();
        assertEquals(1, pubDAOS.size());
        assertEquals(pubDAOS.get(0), pubDAO1);

    }

}
