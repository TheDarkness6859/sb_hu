package com.chat.sbhu.service;

import com.chat.sbhu.models.Event;
import com.chat.sbhu.models.Venue;
import com.chat.sbhu.repository.EventRepository;
import com.chat.sbhu.repository.VenueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventService {

    private final EventRepository repository;
    private final VenueRepository venueRepository;

    public EventService (EventRepository repository, VenueRepository venueRepository) {

        this.repository = repository;
        this.venueRepository = venueRepository;

    }

    public Event getById (UUID id){

        if (id != null){

            return repository.getById(id);

        }

        return null;

    }

    public List<Event> getAll () {

        return repository.getAll();

    }


    public boolean add (Event event){

        if (event == null){
            return false;
        }

        if (event.getVenue() == null){
            return false;
        }

        Venue venue = venueRepository.getByName(event.getVenue().getName());
        if (venue == null){
            return false;
        }

        event.setVenue(venue);
        return repository.add(event);

    }

    public boolean edit (UUID id, Event event){

        Event exists = repository.getById(id);
        if (exists == null){
            return false;
        }

        if (event.getVenue() == null){
            return false;
        }

        Venue venue = venueRepository.getByName(event.getVenue().getName());
        if (venue == null){
            return false;
        }

        event.setId(id);
        event.setVenue(venue);
        return repository.add(event);

    }

    public boolean delete (UUID id){

        return repository.delete(id);

    }

}
