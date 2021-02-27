package com.scally.beerquest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
public class Pub {

    @JsonProperty("_row_id")
    Integer id;

    @JsonProperty("name")
    String name;

    @JsonProperty("category")
    String category;

    @JsonProperty("url")
    String url;

    @JsonProperty("date")
    LocalDateTime date;

    @JsonProperty("excerpt")
    String exceprt;

    @JsonProperty("thumbnail")
    String thumbnail;

    @JsonProperty("lat")
    Double latitude;

    @JsonProperty("lng")
    Double longitude;

    @JsonProperty("address")
    String address;

    @JsonProperty("phone")
    String phone;

    @JsonProperty("twitter")
    String twitter;

    @JsonProperty("stars_beer")
    Double starsBeer;

    @JsonProperty("stars_atmosphere")
    Double starsAtmosphere;

    @JsonProperty("stars_amenities")
    Double starsAmenities;

    @JsonProperty("stars_value")
    Double starsValue;

    @JsonProperty("tags")
    String tags;

}
