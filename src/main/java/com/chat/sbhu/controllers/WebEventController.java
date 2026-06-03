package com.chat.sbhu.controllers;

import com.chat.sbhu.dto.EventDto;
import com.chat.sbhu.models.Category;
import com.chat.sbhu.models.Event;
import com.chat.sbhu.models.Venue;
import com.chat.sbhu.models.enums.City;
import com.chat.sbhu.service.CategoryService;
import com.chat.sbhu.service.EventService;
import com.chat.sbhu.service.VenueService;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/admin/events")
public class WebEventController {

    private final EventService service;
    private final VenueService venueService;
    private final CategoryService categoryService;

    public WebEventController (EventService service, VenueService venueService, CategoryService categoryService){
        this.service = service;
        this.venueService = venueService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getEvents (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String searchName,
            @RequestParam(required = false) City searchCity,
            Model model){

        Slice<EventDto> eventSlice;

        if ((searchName != null && !searchName.trim().isEmpty()) || searchCity != null) {

            String cleanedName = (searchName != null) ? searchName.trim() : null;
            eventSlice = service.searchEvents(cleanedName, searchCity, page, size);

            model.addAttribute("searchName", searchName);
            model.addAttribute("searchCity", searchCity);

        }else {

            eventSlice = service.getCatalog(page, size);

        }

        model.addAttribute("events", eventSlice.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("slice", eventSlice);
        model.addAttribute("cities", City.values());
        model.addAttribute("allCategories", categoryService.getAll());

        return "admin";

    }

    @PostMapping("/create")
    public String create (
            @RequestParam("name") String name,
            @RequestParam("venue.name") String venueName,
            @RequestParam("category") Set<Category> category
            ){

        Event event = new Event();

        event.setName(name);
        event.setCategories(category);

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
