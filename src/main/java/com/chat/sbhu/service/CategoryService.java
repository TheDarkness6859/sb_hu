package com.chat.sbhu.service;

import com.chat.sbhu.models.Category;
import com.chat.sbhu.models.Event;
import com.chat.sbhu.repository.CategoryRepository;
import com.chat.sbhu.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    private final CategoryRepository repository;
    private final EventRepository eventRepository;

    public CategoryService (EventRepository eventRepository, CategoryRepository repository){
        this.eventRepository = eventRepository;
        this.repository = repository;
    }

    public List<Category> getAll(){

        return repository.findAll();

    }

    public Category getById (UUID id){

        if (id != null){

            return repository.getReferenceById(id);

        }

        return null;

    }

    public Category getByName (String name){

        return repository.getByName(name).orElse(null);

    }

    public Category save (Category category){

        if (category == null){

            throw new NullPointerException("The category can be empty");

        }

        return repository.save(category);

    }

    public Category edit (UUID id, Category category){

        Category exits = repository.getReferenceById(id);

        exits.setDescription(category.getDescription());
        exits.setName(category.getName());

        exits.getEvents().clear();

        if (category.getEvents() != null && !category.getEvents().isEmpty()){

            category.getEvents().forEach(events -> {

                if (events.getId() != null){

                    Event event = eventRepository.getReferenceById(id);
                    exits.getEvents().add(event);

                }

            }

            );

        }

        return exits;

    }

    public void delete (UUID id){

        Category exits = repository.getReferenceById(id);

        repository.deleteById(id);

    }


}
