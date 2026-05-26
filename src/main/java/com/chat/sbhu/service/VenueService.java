package com.chat.sbhu.service;

import com.chat.sbhu.models.Venue;
import com.chat.sbhu.repository.VenueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<Venue> getAll () {

        return repository.getAll();

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
}
