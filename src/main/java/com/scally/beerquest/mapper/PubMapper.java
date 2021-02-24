package com.scally.beerquest.mapper;

import com.scally.beerquest.model.Pub;
import com.scally.beerquest.model.PubDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PubMapper {

    public PubDAO toPubDao(Pub pub) {
        return PubDAO.builder()
                .id(pub.getId())
                .name(pub.getName())
                .category(pub.getCategory())
                .address(pub.getAddress())
                .date(pub.getDate())
                .exceprt(pub.getExceprt())
                .latitude(pub.getLatitude())
                .longitude(pub.getLongitude())
                .starsAmenities(pub.getStarsAmenities())
                .starsAtmosphere(pub.getStarsAtmosphere())
                .starsBeer(pub.getStarsBeer())
                .starsValue(pub.getStarsValue())
                .tags(pub.getTags())
                .phone(pub.getPhone())
                .thumbnail(pub.getThumbnail())
                .twitter(pub.getTwitter())
                .url(pub.getUrl())
                .build();
    }

    public List<PubDAO> toPubDaoList(List<Pub> pubs) {
        return pubs.stream()
                .map(this::toPubDao)
                .collect(Collectors.toList());
    }

    public Pub toPub(PubDAO pubDao) {
        return Pub.builder()
                .id(pubDao.getId())
                .address(pubDao.getAddress())
                .name(pubDao.getName())
                .category(pubDao.getCategory())
                .exceprt(pubDao.getExceprt())
                .latitude(pubDao.getLatitude())
                .longitude(pubDao.getLongitude())
                .date(pubDao.getDate())
                .phone(pubDao.getPhone())
                .starsAmenities(pubDao.getStarsAmenities())
                .starsAtmosphere(pubDao.getStarsAtmosphere())
                .starsBeer(pubDao.getStarsBeer())
                .starsValue(pubDao.getStarsValue())
                .tags(pubDao.getTags())
                .thumbnail(pubDao.getThumbnail())
                .url(pubDao.getUrl())
                .twitter(pubDao.getTwitter())
                .build();
    }

    public List<Pub> toPubList(List<PubDAO> pubDAOList) {
        return pubDAOList.stream()
                .map(this::toPub)
                .collect(Collectors.toList());
    }
}
