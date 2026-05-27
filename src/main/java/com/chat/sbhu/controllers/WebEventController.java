package com.chat.sbhu.controllers;

import com.chat.sbhu.dto.EventDto;
import com.chat.sbhu.models.Event;
import com.chat.sbhu.models.Venue;
import com.chat.sbhu.service.EventService;
import com.chat.sbhu.service.VenueService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/events")
public class WebEventController {

    private final EventService service;
    private final VenueService venueService;

    public WebEventController (EventService service, VenueService venueService){
        this.service = service;
        this.venueService = venueService;
    }

    @GetMapping
    public String getEvents (
            @PageableDefault(page = 0, size = 10 ,sort = "name") Pageable pageable,
            Model model){

        Page<Event> eventsPage = service.getAll(pageable);

        model.addAttribute("events", eventsPage.getContent());

        model.addAttribute("currentPage", eventsPage.getNumber());
        model.addAttribute("totalPages", eventsPage.getTotalPages());
        model.addAttribute("totalItems", eventsPage.getTotalElements());

        if (pageable.getSort().isSorted()) {
            model.addAttribute("sortField", pageable.getSort().iterator().next().getProperty());
            model.addAttribute("sortDir", pageable.getSort().iterator().next().getDirection().name().toLowerCase());
        }

        return "admin";

    }

    @PostMapping("/create")
    public String create (
            @RequestParam("name") String name,
            @RequestParam("venue.name") String venueName,
            @RequestParam("type") String type
            ){

        Event event = new Event();

        event.setName(name);
        event.setType(type);

        if (venueName != null && !venueName.trim().isEmpty()){

            Venue venue = venueService.getByName(venueName);

            if (venue != null){

                event.setVenue(venue);

            }

        }

        service.add(event);

        return "redirect:/admin/events";

    }

}
