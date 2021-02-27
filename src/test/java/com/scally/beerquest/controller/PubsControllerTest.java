package com.scally.beerquest.controller;

import com.scally.beerquest.mapper.PubMapper;
import com.scally.beerquest.model.Pub;
import com.scally.beerquest.model.PubDAO;
import com.scally.beerquest.repository.PubRepository;
import com.scally.beerquest.service.BeerQuestService;
import org.assertj.core.api.Assertions;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PubsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BeerQuestService mockService;

    @Autowired
    private PubMapper mapper;

    @Autowired
    private PubRepository repository;


    @BeforeAll
    public void setUp(){
        loadIntegrationData();
    }

    @Test
    void shouldCallToGetAllPubsWhenHitPubsEndpointAndReturnAllPubs() throws Exception {

        MvcResult result = mockMvc.perform(get("/pubs")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String body = result.getResponse().getContentAsString();
        Assertions.assertThat(body).contains("Far Pub", "Near Pub", "Mid Pub", "Good Beer Pub",
                "OK Beer Pub", "Bad Beer Pub", "Beer Garden Pool Table Pub", "Beer Garden Arcade Games Pub",
                "Arcade Games Jukebox Pub");

    }

    @Test
    void shouldCallToGetSingleClosestPubWhenHitClosestEndpointAndReturnClosestPub() throws Exception {

        Double myLatitude = 53.543;
        Double myLongitude = -1.5507;
        int limit = 1;
        String path = String.format("/pubs/closest?lat=%s&long=%s&limit=%d", myLatitude, myLongitude, limit);

        MvcResult result = mockMvc.perform(get(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String body = result.getResponse().getContentAsString();
        Assertions.assertThat(body).contains("Near Pub");
        Assertions.assertThat(body).doesNotContain("Far Pub", "Mid Pub");

    }

    @Test
    void shouldCallToGetMultipleClosestPubsWhenHitClosestEndpointAndReturnClosestPubs() throws Exception {

        Double myLatitude = 53.543;
        Double myLongitude = -1.5507;
        int limit = 2;
        String path = String.format("/pubs/closest?lat=%s&long=%s&limit=%d", myLatitude, myLongitude, limit);

        MvcResult result = mockMvc.perform(get(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String body = result.getResponse().getContentAsString();
        Assertions.assertThat(body).contains("Near Pub", "Mid Pub");
        Assertions.assertThat(body).doesNotContain("Far Pub");

    }

    @Test
    void shouldReturnBadRequestErrorWhenHitClosestEndpointIfQueryParamsNotSet() throws Exception {

        String path = String.format("/pubs/closest");

        MvcResult result = mockMvc.perform(get(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

    }

    @Test
    void shouldCallToGetTopPubsWhenHitTopEndpointAndReturnTopPubsByCriteria() throws Exception {

        String criteria = "beer";
        String minScore = "3.0";
        int limit = 2;
        String path = String.format("/pubs/top?criteria=%s&minimum-score=%s&limit=%d", criteria, minScore, limit);

        MvcResult result = mockMvc.perform(get(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String body = result.getResponse().getContentAsString();
        Assertions.assertThat(body).contains("Good Beer Pub", "OK Beer Pub");
        Assertions.assertThat(body).doesNotContain("Far Pub", "Near Pub", "Mid Pub", "Bad Beer Pub",
                "Beer Garden Pool Table Pub", "Beer Garden Arcade Games Pub", "Arcade Games Jukebox Pub");

    }

    @Test
    void shouldReturnBadRequestWhenHitTopEndpointIfRatingCriteriaIsInvalid() throws Exception {

        String criteria = "invalid";
        String minScore = "3.0";
        int limit = 2;
        String path = String.format("/pubs/top?criteria=%s&minimum-score=%s&limit=%d", criteria, minScore, limit);

        mockMvc.perform(get(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    void shouldCallToGetTaggedPubsWhenHitTagEndpointAndReturnTaggedPubs() throws Exception {

        String tag = "beer garden";

        String path = String.format("/pubs/tag?tag=%s", tag);

        MvcResult result = mockMvc.perform(get(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String body = result.getResponse().getContentAsString();
        Assertions.assertThat(body).contains("Beer Garden Pool Table Pub", "Beer Garden Arcade Games Pub");
        Assertions.assertThat(body).doesNotContain("Far Pub", "Near Pub", "Mid Pub", "Bad Beer Pub", "Good Beer Pub",
                "OK Beer Pub", "Arcade Games Jukebox Pub");

    }

    @Test
    void shouldCallToGetTaggedPubsWhenHitTagEndpointAndReturnNoPubs() throws Exception {

        String tag = "tag which has no matches";

        String path = String.format("/pubs/tag?tag=%s", tag);

        MvcResult result = mockMvc.perform(get(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String body = result.getResponse().getContentAsString();
        Assertions.assertThat(body).isEqualTo("[]");

    }

    private void loadIntegrationData() {
        Double myLatitude = 53.543;
        Double myLongitude = -1.5507;
        Double farLatitude = 55.9999;
        Double farLongitude = -2.0101;
        Double midLatitude = 54.3333;
        Double midLongitude = -1.8101;

        EasyRandom generator = new EasyRandom();

        Pub farPub = generator.nextObject(Pub.class).toBuilder()
                .name("Far Pub")
                .latitude(farLatitude)
                .longitude(farLongitude)
                .starsBeer(0.0)
                .tags("")
                .build();

        Pub nearPub = generator.nextObject(Pub.class).toBuilder()
                .name("Near Pub")
                .latitude(myLatitude)
                .longitude(myLongitude)
                .starsBeer(0.0)
                .tags("")
                .build();

        Pub midPub = generator.nextObject(Pub.class).toBuilder()
                .name("Mid Pub")
                .latitude(midLatitude)
                .longitude(midLongitude)
                .starsBeer(0.0)
                .tags("")
                .build();

        Pub goodBeerPub = generator.nextObject(Pub.class).toBuilder()
                .name("Good Beer Pub")
                .latitude(0.0)
                .longitude(0.0)
                .starsBeer(5.0)
                .tags("")
                .build();

        Pub okBeerPub = generator.nextObject(Pub.class).toBuilder()
                .name("OK Beer Pub")
                .latitude(0.0)
                .longitude(0.0)
                .starsBeer(3.5)
                .tags("")
                .build();

        Pub badBeerPub = generator.nextObject(Pub.class).toBuilder()
                .name("Bad Beer Pub")
                .latitude(0.0)
                .longitude(0.0)
                .starsBeer(1.5)
                .tags("")
                .build();

        Pub poolTableBeerGardenPub = generator.nextObject(Pub.class).toBuilder()
                .name("Beer Garden Pool Table Pub")
                .latitude(0.0)
                .longitude(0.0)
                .starsBeer(0.0)
                .tags("beer garden, pool table")
                .build();

        Pub poolTableArcadeGamesPub = generator.nextObject(Pub.class).toBuilder()
                .name("Beer Garden Arcade Games Pub")
                .latitude(0.0)
                .longitude(0.0)
                .starsBeer(0.0)
                .tags("beer garden, arcade games")
                .build();

        Pub arcadeGamesJukeboxPub = generator.nextObject(Pub.class).toBuilder()
                .name("Arcade Games Jukebox Pub")
                .latitude(0.0)
                .longitude(0.0)
                .starsBeer(0.0)
                .tags("arcade games, jukebox")
                .build();

        List<Pub> pubs = List.of(farPub, nearPub, midPub, goodBeerPub, okBeerPub, badBeerPub, poolTableBeerGardenPub, poolTableArcadeGamesPub, arcadeGamesJukeboxPub);
        List<PubDAO> pubDAOs = mapper.toPubDaoList(pubs);
        repository.saveAll(pubDAOs);
    }




}
