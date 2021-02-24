package com.scally.beerquest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@JsonDeserialize(builder = Response.ResponseBuilder.class)
@Value
@Builder
@AllArgsConstructor
public class Response {

    @JsonPOJOBuilder(withPrefix = "")
    public static class ResponseBuilder { }

    @JsonProperty("rows")
    private final List<Pub> pubs;

}
