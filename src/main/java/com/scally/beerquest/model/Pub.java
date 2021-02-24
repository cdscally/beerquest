package com.scally.beerquest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
public class Pub {

    @JsonProperty("_row_id")
    private final Integer id;

    @JsonProperty("name")
    private final String name;

    @JsonProperty("category")
    private final String category;

    @JsonProperty("url")
    private final String url;

    @JsonProperty("date")
    private final LocalDateTime date;

    @JsonProperty("excerpt")
    private final String exceprt;

    @JsonProperty("thumbnail")
    private final String thumbnail;

    @JsonProperty("lat")
    private final Double latitude;

    @JsonProperty("lng")
    private final Double longitude;

    @JsonProperty("address")
    private final String address;

    @JsonProperty("phone")
    private final String phone;

    @JsonProperty("twitter")
    private final String twitter;

    @JsonProperty("stars_beer")
    private final Double starsBeer;

    @JsonProperty("stars_atmosphere")
    private final Double starsAtmosphere;

    @JsonProperty("stars_amenities")
    private final Double starsAmenities;

    @JsonProperty("stars_value")
    private final Double starsValue;

    @JsonProperty("tags")
    private final String tags;

}
