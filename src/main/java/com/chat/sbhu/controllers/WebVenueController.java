package com.chat.sbhu.controllers;

import com.chat.sbhu.dto.VenueDto;
import com.chat.sbhu.models.Event;
import com.chat.sbhu.models.Venue;
import com.chat.sbhu.service.VenueService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/venues")
public class WebVenueController {

    private final VenueService service;

    public WebVenueController (VenueService service){
        this.service = service;
    }

    @GetMapping
    public String getEvents (
            @PageableDefault(page = 0, size = 10 ,sort = "name") Pageable pageable,
            Model model){

        Page<Venue> venuePages = service.getAll(pageable);

        model.addAttribute("venues", venuePages.getContent());

        model.addAttribute("currentPage", venuePages.getNumber());
        model.addAttribute("totalPages", venuePages.getTotalPages());
        model.addAttribute("totalItems", venuePages.getTotalElements());

        if (pageable.getSort().isSorted()) {
            model.addAttribute("sortField", pageable.getSort().iterator().next().getProperty());
            model.addAttribute("sortDir", pageable.getSort().iterator().next().getDirection().name().toLowerCase());
        }

        return "venues";

    }

    @PostMapping("/create")
    public String createVenue (@ModelAttribute VenueDto dto){

        Venue venue = new Venue();

        venue.setName(dto.name());
        venue.setAddress(dto.address());
        venue.setCapacity(dto.capacity());

        service.add(venue);

        return "redirect:/admin/venues";

    }

}
