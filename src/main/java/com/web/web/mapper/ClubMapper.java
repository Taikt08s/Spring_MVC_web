package com.web.web.mapper;

import com.web.web.dto.ClubDTO;
import com.web.web.models.Club;

import java.util.stream.Collectors;

import static com.web.web.mapper.EventMapper.mapToEventDTO;

public class ClubMapper {
    public static Club mapToClub(ClubDTO club) {
        Club clubDto=Club.builder()
                .id(club.getId())
                .title(club.getTitle())
                .photoUrl(club.getPhotoUrl())
                .content(club.getContent())
                .createdBy(club.getCreatedBy())
                .createdOn(club.getCreatedOn())
                .updatedOn(club.getUpdatedOn())
                .build();
        return clubDto;
    }

    public static ClubDTO mapToClubDTO(Club club) {
        ClubDTO clubDTO = ClubDTO.builder()
                .id(club.getId())
                .title(club.getTitle())
                .photoUrl(club.getPhotoUrl())
                .content(club.getContent())
                .createdBy(club.getCreatedBy())
                .createdOn(club.getCreatedOn())
                .updatedOn(club.getUpdatedOn())
                .events(club.getEvents().stream().map((event) -> mapToEventDTO(event)).collect(Collectors.toList()))
                .build();
        return clubDTO;
    }
}
