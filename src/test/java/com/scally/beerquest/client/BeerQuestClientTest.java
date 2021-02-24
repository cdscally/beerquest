package com.scally.beerquest.client;

import com.scally.beerquest.config.RestTemplateConfig;
import com.scally.beerquest.model.Pub;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@Slf4j
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RestTemplateConfig.class, BeerQuestClient.class})
@AutoConfigureWebClient
public class BeerQuestClientTest {

    @Autowired
    private BeerQuestClient underTest;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockRestServiceServer;

    @Before
    public void init() {
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void shouldCallBeerQuestApiAndExtractPubsListFromResponse() throws URISyntaxException {

        InputStream is = this.getClass().getResourceAsStream("/beer-quest-response.json");
        String text = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)).lines()
                .collect(Collectors.joining("\n"));

        mockRestServiceServer.expect(ExpectedCount.once(),
                requestTo(new URI("https://datamillnorth.org/api/table/wk7xz_hf8bv?$limit=500")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(text)
                );

        List<Pub> pubList = underTest.loadPubData();
        assertEquals(pubList.size(), 10);

        List<Integer> ids = pubList.stream().map(pub -> pub.getId()).collect(Collectors.toList());
        List<Integer> expectedIds = List.of(1,2,3,4,5,6,7,8,9,10);
        assertEquals(ids, expectedIds);
    }

}
