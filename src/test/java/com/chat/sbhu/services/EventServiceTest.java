package com.chat.sbhu.services;

import com.chat.sbhu.models.Event;
import com.chat.sbhu.models.Venue;
import com.chat.sbhu.repository.EventRepository;
import com.chat.sbhu.repository.VenueRepository;
import com.chat.sbhu.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private VenueRepository venueRepository;

    @InjectMocks
    private EventService eventService;

    private Event validEvent;
    private Venue validVenue;

    @BeforeEach
    void setUp() {
        validVenue = new Venue();
        validVenue.setName("Tremendo");

        validEvent = new Event();
        validEvent.setName("Rock Concert");
        validEvent.setVenue(validVenue);
    }

    @Test
    @DisplayName("Scenario 1: Successful Registration (Happy Path)")
    void registerEventSuccessfully() {
        when(venueRepository.getByName("Tremendo")).thenReturn(validVenue);
        when(eventRepository.add(any(Event.class))).thenReturn(true);

        boolean result = eventService.add(validEvent);

        assertTrue(result);
        assertEquals(validVenue, validEvent.getVenue());
        verify(eventRepository, times(1)).add(validEvent);
    }

    @Test
    @DisplayName("Scenario 2: Invalid Registration Attempt - Empty Name (Error Path)")
    void registerEventInvalidWithEmptyName() {
        validEvent.setName("");

        boolean result = eventService.add(validEvent);

        assertFalse(result);
        verify(eventRepository, never()).add(any(Event.class));
    }

    @Test
    @DisplayName("Scenario 3: Empty Catalog Query (Edge Case)")
    void getAllEventsWhenDatabaseIsEmpty() {

        Pageable pageable = PageRequest.of(0, 10);

        when(eventRepository.getAllPaginated(any(Pageable.class))).thenReturn(null);

        Page<Event> result = eventService.getAll(pageable);

        assertNull(result);
        verify(eventRepository, times(1)).getAllPaginated(pageable);
    }
}