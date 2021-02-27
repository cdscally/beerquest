package com.scally.beerquest.client;

import com.scally.beerquest.model.Pub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class BeerQuestClient {

    private static final String BASE_URL = "https://datamillnorth.org/api/table/wk7xz_hf8bv?$limit=500";

    @Autowired
    private RestTemplate restTemplate;

    public List<Pub> loadPubData(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-version", "2");
        HttpEntity<Response> entity = new HttpEntity<>(headers);

        ResponseEntity<Response> responseEntity = restTemplate.exchange(BASE_URL, HttpMethod.GET, entity, Response.class);

        Response response = responseEntity.getBody();
        return response.getPubs();
    }

}
