package com.web.web.controller;

import com.web.web.dto.ClubDTO;
import com.web.web.dto.EventDTO;
import com.web.web.models.Event;
import com.web.web.models.UserEntity;
import com.web.web.security.SecurityUtil;
import com.web.web.services.EventService;
import com.web.web.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class EventController {
    private EventService eventService;
    private UserService userService;

    @Autowired
    public EventController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    @GetMapping("/events/{clubID}/new")
    public String createEventForm(@PathVariable("clubID") long clubID, Model model) {
        Event event = new Event();
        model.addAttribute("clubID", clubID);
        model.addAttribute("event", event);
        return "event-create";
    }

    @PostMapping("/events/{clubID}")
    public String createEvent(@PathVariable("clubID") long clubID, @ModelAttribute("event") EventDTO eventDTO
            , Model model, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("event", eventDTO);
            return "club-create";
        }
        eventService.createEvent(clubID, eventDTO);
        return "redirect:/clubs/" + clubID;
    }

    @GetMapping("/events")
    public String eventList(Model model) {
        UserEntity user = new UserEntity();
        List<EventDTO> events = eventService.findAllEvents();
        String username = SecurityUtil.getSessionUser();
        if (username != null) {
            user = userService.findByUserName(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("events", events);
        return "event-list";
    }

    @GetMapping("/events/{eventID}")
    public String viewEvent(@PathVariable("eventID") long eventID, Model model) {
        UserEntity user = new UserEntity();
        EventDTO eventDTO = eventService.findEventID(eventID);
        String username = SecurityUtil.getSessionUser();
        if (username != null) {
            user = userService.findByUserName(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("club",eventDTO);
        model.addAttribute("user", user);
        model.addAttribute("event", eventDTO);
        return "event-detail";
    }

    @GetMapping("/events/{eventID}/edit")
    public String editEventForm(@PathVariable("eventID") long eventID, Model model) {
        EventDTO event = eventService.findEventID(eventID);
        model.addAttribute("club", event);
        return "events-edit";
    }

    @PostMapping("/events/{eventID}/edit")
    public String updateEvent(@PathVariable("eventID") long eventID,
                              @Valid @ModelAttribute("club") EventDTO event,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("event", event);
            return "events-edit";
        }
        EventDTO eventDTO = eventService.findEventID(eventID);
        event.setId(eventID);
        event.setClub(eventDTO.getClub());
        eventService.updateEvent(event);
        return "redirect:/events";
    }

    @GetMapping("/events/{eventID}/delete")
    public String deleteEvent(@PathVariable("eventID") long eventID) {
        eventService.deleteEvent(eventID);
        return "redirect:/events";
    }
}
