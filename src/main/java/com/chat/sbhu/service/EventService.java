package com.chat.sbhu.service;

import com.chat.sbhu.models.Event;
import com.chat.sbhu.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventService {

    private final EventRepository repository;

    public EventService (EventRepository repository) {

        this.repository = repository;

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

        if (event != null){

            return repository.add(event);

        }

        return false;

    }

    public boolean delete (UUID id){

        return repository.delete(id);

    }

}
