package com.web.web.services.impl;

import com.web.web.dto.EventDTO;
import com.web.web.models.Club;
import com.web.web.models.Event;
import com.web.web.respository.ClubRepository;
import com.web.web.respository.EventRepository;
import com.web.web.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.web.web.mapper.EventMapper.mapToEvent;
import static com.web.web.mapper.EventMapper.mapToEventDTO;

@Service
public class EventImplement implements EventService {
    private EventRepository eventRepository;
    private ClubRepository clubRepository;

    @Autowired
    public EventImplement(EventRepository eventRepository, ClubRepository clubRepository) {
        this.eventRepository = eventRepository;
        this.clubRepository = clubRepository;
    }

    @Override
    public void createEvent(Long clubID, EventDTO eventDTO) {
        Club club = clubRepository.findById(clubID).get();
        Event event = mapToEvent(eventDTO);
        event.setClub(club);
        eventRepository.save(event);
    }

    @Override
    public List<EventDTO> findAllEvents() {
        List<Event> events= eventRepository.findAll();
        return events.stream().map(event -> mapToEventDTO(event)).collect(Collectors.toList());
    }

    @Override
    public EventDTO findEventID(long eventID) {
        Event event= eventRepository.findById(eventID).get();
        return mapToEventDTO(event);
    }

    @Override
    public void updateEvent(EventDTO eventDto) {
        Event event=mapToEvent(eventDto);
        eventRepository.save(event);
    }

    @Override
    public void deleteEvent(long eventID) {
        eventRepository.deleteById(eventID);
    }

}
