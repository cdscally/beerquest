package com.scally.beerquest.model;

import java.util.Optional;

public enum RatingCriteria {

    BEER ("beer"),
    ATMOSPHERE ("atmosphere"),
    AMENITIES ("amenities"),
    VALUE ("value");

    private final String queryParam;

    RatingCriteria(final String queryParam) {this.queryParam = queryParam;}

    public String getQueryParam() {return this.queryParam;}

    public static Optional<RatingCriteria> fromQueryParam(final String queryParam) {
        for (RatingCriteria ratingCriteria : RatingCriteria.values()) {
            if (ratingCriteria.queryParam.equals(queryParam)) {
                return Optional.of(ratingCriteria);
            }
        }
        return Optional.empty();
    }

}
