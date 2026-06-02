package com.chat.sbhu.service;

import com.chat.sbhu.models.Venue;
import com.chat.sbhu.repository.VenueRepository;
import jakarta.transaction.Transactional;
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

            return repository.getReferenceById(id);

        }

        return null;

    }

    public Venue getByName (String name){

        return repository.getByName(name).orElse(null);

    }

    public Page<Venue> getAll (Pageable pageable) {

        return repository.getAllPaginated(pageable);

    }


    public Venue add (Venue event){

        if (event != null){

            return repository.save(event);

        }

        return null;

    }

    @Transactional
    public boolean delete (UUID id){

        repository.deleteById(id);

        return true;

    }

    @Transactional
    public Venue edit (UUID id, Venue venue){

        Venue exists = repository.getReferenceById(id);

        exists.setName(venue.getName());
        exists.setCapacity(venue.getCapacity());
        exists.setAddress(venue.getAddress());
        exists.setCity(venue.getCity());

        return exists;

    }

}
