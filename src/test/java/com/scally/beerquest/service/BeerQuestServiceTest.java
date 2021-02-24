package com.scally.beerquest.service;

import com.scally.beerquest.mapper.PubMapper;
import com.scally.beerquest.model.Pub;
import com.scally.beerquest.model.PubDAO;
import com.scally.beerquest.repository.PubRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class BeerQuestServiceTest {

    @InjectMocks
    private BeerQuestService underTest;

    @Mock
    private PubRepository mockPubRepository;

    @Mock
    private PubMapper mockMapper;

    @Test
    void shouldGetAllPubs(){

        int listSize = 10;
        EasyRandom generator = new EasyRandom();
        List<PubDAO> pubDaoList = generator.objects(PubDAO.class, listSize)
                .collect(Collectors.toList());

        when(mockPubRepository.findAll()).thenReturn(pubDaoList);

        underTest.getAllPubs();

        verify(mockMapper, times(1)).toPubList(pubDaoList);

    }

    @Test
    void shouldGetClosestPubsInOrderOfProximity(){

        Double myLatitude = 53.543;
        Double myLongitude = -1.5507;
        Double farLatitude = 55.9999;
        Double farLongitude = -2.0101;
        Double midLatitude = 54.3333;
        Double midLongitude = -1.8101;

        EasyRandom generator = new EasyRandom();

        Pub farPub = generator.nextObject(Pub.class).toBuilder()
                .latitude(farLatitude)
                .longitude(farLongitude)
                .build();

        Pub nearPub = generator.nextObject(Pub.class).toBuilder()
                .latitude(myLatitude)
                .longitude(myLongitude)
                .build();

        Pub midPub = generator.nextObject(Pub.class).toBuilder()
                .latitude(midLatitude)
                .longitude(midLongitude)
                .build();

        List<Pub> pubs = List.of(farPub, nearPub, midPub);
        when(mockMapper.toPubList(any())).thenReturn(pubs);

        List<Pub> singleClosestPub = underTest.getClosestPubs(myLatitude, myLongitude, 1);
        assertEquals( 1, singleClosestPub.size());
        assertEquals(nearPub, singleClosestPub.get(0));

        List<Pub> multipleClosestPubs = underTest.getClosestPubs(myLatitude, myLongitude, 2);
        assertEquals(2, multipleClosestPubs.size());

        List<Pub> allClosestPubs = underTest.getClosestPubs(myLatitude, myLongitude, 10);
        assertEquals(3, allClosestPubs.size());
        assertEquals(nearPub, allClosestPubs.get(0));
        assertEquals(midPub, allClosestPubs.get(1));
        assertEquals(farPub, allClosestPubs.get(2));

    }


    @Test
    void shouldGetPubsByBeerRatingOrderedByBeerRating(){

        Double minScore = 1.0;

        EasyRandom generator = new EasyRandom();

        Pub goodBeerPub = generator.nextObject(Pub.class).toBuilder()
                .starsBeer(5.0)
                .build();

        Pub okBeerPub = generator.nextObject(Pub.class).toBuilder()
                .starsBeer(3.5)
                .build();

        Pub badBeerPub = generator.nextObject(Pub.class).toBuilder()
                .starsBeer(1.5)
                .build();

        List<Pub> pubs = List.of(okBeerPub, badBeerPub, goodBeerPub);
        when(mockPubRepository.findAllByStarsBeerGreaterThanEqual(minScore)).thenReturn(Optional.of(List.of(new PubDAO())));
        when(mockMapper.toPubList(any())).thenReturn(pubs);

        List<Pub> topPubByBeerRating = underTest.getPubsByBeerRating(minScore, 1);
        assertEquals( 1, topPubByBeerRating.size());
        assertEquals(goodBeerPub, topPubByBeerRating.get(0));

        List<Pub> multiplePubsByBeerRating = underTest.getPubsByBeerRating(minScore, 2);
        assertEquals(2, multiplePubsByBeerRating.size());
        assertEquals(goodBeerPub, multiplePubsByBeerRating.get(0));
        assertEquals(okBeerPub, multiplePubsByBeerRating.get(1));

        List<Pub> allPubsByBeerRating = underTest.getPubsByBeerRating(minScore, 10);
        assertEquals(3, allPubsByBeerRating.size());
        assertEquals(goodBeerPub, allPubsByBeerRating.get(0));
        assertEquals(okBeerPub, allPubsByBeerRating.get(1));
        assertEquals(badBeerPub, allPubsByBeerRating.get(2));

    }



}
