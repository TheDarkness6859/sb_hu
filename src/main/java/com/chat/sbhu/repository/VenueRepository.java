package com.chat.sbhu.repository;

import com.chat.sbhu.models.Venue;
import com.chat.sbhu.repository.persistance.VenueDatabase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class VenueRepository {

    private final VenueDatabase dataBase;

    public VenueRepository (VenueDatabase dataBase){
        this.dataBase = dataBase;
    }

    public Page<Venue> getAllPaginated (Pageable pageable) {

        return dataBase.findAll(pageable);

    }

    public Venue getById (UUID id){

        return dataBase.getReferenceById(id);

    }

    public Venue getByName (String name){

        return dataBase.findAll()
                .stream()
                .filter(venue -> name.equalsIgnoreCase(venue.getName()))
                .findFirst()
                .orElse(null)
        ;

    }

    public boolean add (Venue venue){

        dataBase.save(venue);

        return true;

    }

    public boolean delete (UUID id){

       dataBase.deleteById(id);

       return true;

    }

}
