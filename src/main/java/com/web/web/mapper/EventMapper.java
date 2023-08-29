package com.web.web.mapper;

import com.web.web.dto.EventDTO;
import com.web.web.models.Event;

public class EventMapper {
    public static Event mapToEvent(EventDTO eventDTO) {
        return Event.builder()
                .id(eventDTO.getId())
                .name(eventDTO.getName())
                .startTime(eventDTO.getStartTime())
                .endTime(eventDTO.getEndTime())
                .type(eventDTO.getType())
                .photoUrl(eventDTO.getPhotoUrl())
                .createdOn(eventDTO.getCreatedOn())
                .updatedOn(eventDTO.getUpdatedOn())
                .club(eventDTO.getClub())
                .build();
    }
    public static EventDTO mapToEventDTO(Event event) {
        return EventDTO.builder()
                .id(event.getId())
                .name(event.getName())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .type(event.getType())
                .photoUrl(event.getPhotoUrl())
                .createdOn(event.getCreatedOn())
                .updatedOn(event.getUpdatedOn())
                .club(event.getClub())
                .build();
    }
}
