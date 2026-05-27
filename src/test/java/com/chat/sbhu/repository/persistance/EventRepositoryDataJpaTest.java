package com.chat.sbhu.repository.persistance;

import com.chat.sbhu.models.Event;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EventRepositoryDataJpaTest {

    @Autowired
    private EventDatabase eventDatabase;

    @Test
    @DisplayName("Scenario 1: Real Database Persistence Verification")
    void testEventPersistence() {
        Event event = new Event();
        event.setName("Rock Concert Medellin");
        event.setType("Musical");

        Event savedEvent = eventDatabase.save(event);
        assertNotNull(savedEvent.getId());

        Optional<Event> foundEvent = eventDatabase.findById(savedEvent.getId());

        assertTrue(foundEvent.isPresent());
        assertEquals("Rock Concert Medellin", foundEvent.get().getName());
    }

    @Test
    @DisplayName("Scenario 3: Results Pagination and Sorting Volume Test")
    void testEventPaginationAndSorting() {
        for (int i = 1; i <= 6; i++) {
            Event e = new Event();
            e.setName("Event " + i);
            e.setType("Social");
            eventDatabase.save(e);
        }

        Pageable pageable = PageRequest.of(0, 5, Sort.by("name").ascending());
        Page<Event> eventPage = eventDatabase.findAll(pageable);

        assertEquals(5, eventPage.getContent().size());
        assertEquals(6, eventPage.getTotalElements());
        assertEquals(2, eventPage.getTotalPages());
        assertEquals("Event 1", eventPage.getContent().getFirst().getName());
    }
}