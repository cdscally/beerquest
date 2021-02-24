package com.scally.beerquest.service;

import com.scally.beerquest.client.BeerQuestClient;
import com.scally.beerquest.mapper.PubMapper;
import com.scally.beerquest.model.Pub;
import com.scally.beerquest.model.PubDAO;
import com.scally.beerquest.repository.PubRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class DataLoaderTest {

    @InjectMocks
    private DataLoader underTest;

    @Mock
    private BeerQuestClient mockBeerQuestClient;

    @Mock
    private PubMapper mockPubMapper;

    @Mock
    private PubRepository mockPubRepository;

    @Test
    void shouldMapListOfPubsAndCallRepositoryToPersistPubDAOsToDatabase(){

        int listSize = 5;
        EasyRandom generator = new EasyRandom();
        List<Pub> pubList = generator.objects(Pub.class, listSize)
                .collect(Collectors.toList());
        List<PubDAO> pubDaoList = generator.objects(PubDAO.class, listSize)
                .collect(Collectors.toList());

        when(mockBeerQuestClient.loadPubData()).thenReturn(pubList);
        when(mockPubMapper.toPubDaoList(any())).thenReturn(pubDaoList);

        ContextRefreshedEvent event=mock(ContextRefreshedEvent.class);
        underTest.onApplicationEvent(event);

        verify(mockPubMapper, Mockito.times(1)).toPubDaoList(pubList);
        verify(mockPubRepository, Mockito.times(1)).saveAll(pubDaoList);

    }

}
