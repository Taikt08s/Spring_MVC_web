package com.web.web.services;

import com.web.web.dto.EventDTO;

import java.util.List;

public interface EventService {
    void createEvent (Long clubID, EventDTO eventDTO);
    List<EventDTO> findAllEvents();
    EventDTO findEventID(long eventID);
    void updateEvent(EventDTO eventDto);
    void deleteEvent(long eventID);
}
