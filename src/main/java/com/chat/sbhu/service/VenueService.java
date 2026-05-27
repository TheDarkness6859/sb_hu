package com.chat.sbhu.service;

import com.chat.sbhu.models.Venue;
import com.chat.sbhu.repository.VenueRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VenueService {

    private final VenueRepository repository;

    public VenueService (VenueRepository repository) {

        this.repository = repository;

    }

    public Venue getById (UUID id){

        if (id != null){

            return repository.getById(id);

        }

        return null;

    }

    public Page<Venue> getAll (Pageable pageable) {

        return repository.getAllPaginated(pageable);

    }


    public boolean add (Venue event){

        if (event != null){

            return repository.add(event);

        }

        return false;

    }

    public boolean delete (UUID id){

        return repository.delete(id);

    }

    public boolean edit (UUID id, Venue venue){

        Venue exists = repository.getById(id);

        if (exists != null){

            venue.setId(id);
            return  repository.add(venue);

        }

        return false;

    }

}
