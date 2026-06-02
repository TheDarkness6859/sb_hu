package com.chat.sbhu.service;

import com.chat.sbhu.dto.EventDto;
import com.chat.sbhu.models.Event;
import com.chat.sbhu.models.Venue;
import com.chat.sbhu.repository.EventRepository;
import com.chat.sbhu.repository.VenueRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EventService {

    private final EventRepository repository;
    private final VenueRepository venueRepository;

    public EventService (EventRepository repository, VenueRepository venueRepository) {

        this.repository = repository;
        this.venueRepository = venueRepository;

    }

    public EventDto getById (UUID id){

        if (id != null){

            Event e = repository.getReferenceById(id);
            return new EventDto(e.getId(), e.getName(), e.getVenue().getName(), e.getDate(), e.getVenue().getCity());

        }

        return null;

    }

    public Slice<EventDto> getCatalog (int page, int size){

        return repository.findEventCatalog(PageRequest.of(page, size));

    }


    public Event add (Event event){

        if (event == null || event.getVenue() == null){
            throw new IllegalArgumentException("Invalid data");
        }

        Venue venue = venueRepository.getByName(event.getVenue().getName())
                .orElseThrow(() -> new RuntimeException("The Venue don't exits"));

        event.setVenue(venue);

        return repository.save(event);

    }

    @Transactional
    public Event edit (UUID id, Event event){

        Event exists = repository.getReferenceById(id);

        if (event.getVenue() == null){
            throw new IllegalArgumentException("The Venue is required");
        }

        Venue venue = venueRepository.getByName(event.getVenue().getName())
                .orElseThrow(() -> new RuntimeException("The Venue don't exits"));

        exists.setName(event.getName());
        exists.setCategories(event.getCategories());
        exists.setVenue(venue);

        return exists;

    }

    @Transactional
    public boolean delete (UUID id){

        Event event = repository.getReferenceById(id);

        if (event == null) return false;

        event.softDelete();
        return true;

    }

}
