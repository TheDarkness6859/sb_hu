package com.chat.sbhu.repository;

import com.chat.sbhu.models.Venue;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class VenueRepository {

    private final Map<UUID, Venue> database = new HashMap<>();

    public List<Venue> getAll () {

        if (database.isEmpty()){

            return null;

        }

        return new ArrayList<>(database.values());

    }

    public Venue getById (UUID id){

        return database.get(id);

    }

    public boolean add (Venue venue){

        return database.put(venue.getId(), venue) != null;

    }

    public boolean delete (UUID id){

        return database.remove(id) != null;

    }

}
