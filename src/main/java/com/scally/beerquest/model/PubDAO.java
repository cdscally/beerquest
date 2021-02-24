package com.scally.beerquest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pubs")
@Data
@Builder
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
