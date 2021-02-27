package com.scally.beerquest.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "pubs")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class PubDAO {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "url")
    private String url;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "excerpt")
    private String exceprt;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "twitter")
    private String twitter;

    @Column(name = "stars_beer")
    private Double starsBeer;

    @Column(name = "stars_atmosphere")
    private Double starsAtmosphere;

    @Column(name = "stars_amenities")
    private Double starsAmenities;

    @Column(name = "stars_value")
    private Double starsValue;

    @Column(name = "tags")
    private String tags;

}
