package com.chat.sbhu.repository;

import com.chat.sbhu.models.Event;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class EventRepository {

    private final Map<UUID, Event> database = new HashMap<>();

    public List<Event> getAll () {

        if (database.isEmpty()){

            return null;

        }

        return new ArrayList<>(database.values());

    }

    public Event getById (UUID id){

        return database.get(id);

    }

    public Event getByName (String name){

        return database.values()
                .stream()
                .filter(event -> name.equalsIgnoreCase(event.getName()))
                .findFirst()
                .orElse(null)
        ;

    }

    public boolean add (Event event) {

        if (event.getId() == null){

            event.setId(UUID.randomUUID());

        }

        database.put(event.getId(), event);

        return true;

    }

    public boolean delete (UUID id){

        return database.remove(id) != null;

    }

}
