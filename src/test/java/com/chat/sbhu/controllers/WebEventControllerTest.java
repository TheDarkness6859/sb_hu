package com.chat.sbhu.controllers;

import com.chat.sbhu.models.Event;
import com.chat.sbhu.models.Venue;
import com.chat.sbhu.service.EventService;
import com.chat.sbhu.service.VenueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class WebEventControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EventService eventService;

    @Mock
    private VenueService venueService;

    @InjectMocks
    private WebEventController webEventController;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(webEventController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    @DisplayName("Scenario 1 & 4: Centralized Display and Model Attribute Validation (Happy Path)")
    void getEventsWithExistingDataShouldRenderTable() throws Exception {
        Venue mockVenue = new Venue();
        mockVenue.setName("Tremendo");

        Event mockEvent = new Event();
        mockEvent.setName("Twenty One Pilots Show");
        mockEvent.setType("Musical");
        mockEvent.setVenue(mockVenue);

        List<Event> eventList = List.of(mockEvent);
        Page<Event> mockPage = new PageImpl<>(eventList, PageRequest.of(0, 10), 1);

        when(eventService.getAll(any())).thenReturn(mockPage);

        mockMvc.perform(get("/admin/events"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"))
                .andExpect(model().attributeExists("events"))
                .andExpect(model().attribute("events", hasSize(1)))
                .andExpect(model().attribute("events", hasItem(
                        hasProperty("name", is("Twenty One Pilots Show"))
                )));

        verify(eventService, times(1)).getAll(any());
    }

    @Test
    @DisplayName("Scenario 2: Empty Inventory Management (Edge Case)")
    void getEventsWhenDatabaseIsEmptyShouldShowFriendlyMessage() throws Exception {
        Page<Event> emptyPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 10), 0);

        when(eventService.getAll(any())).thenReturn(emptyPage);

        mockMvc.perform(get("/admin/events"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"))
                .andExpect(model().attribute("events", hasSize(0)));

        verify(eventService, times(1)).getAll(any());
    }

    @Test
    @DisplayName("Scenario 3: Successful Registration and Automatic Redirection")
    void createEventSuccessfullyAndRedirect() throws Exception {
        String eventName = "Tech Talk";
        String eventType = "Educational";
        String venueName = "Auditorio Ruta N";

        Venue mockVenue = new Venue();
        mockVenue.setName(venueName);

        when(venueService.getByName(venueName)).thenReturn(mockVenue);

        mockMvc.perform(post("/admin/events/create")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", eventName)
                        .param("type", eventType)
                        .param("venue.name", venueName))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/events"));

        ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(eventService, times(1)).add(eventCaptor.capture());

        Event savedEvent = eventCaptor.getValue();
        assertEquals(eventName, savedEvent.getName());
        assertEquals(eventType, savedEvent.getType());
        assertEquals(mockVenue, savedEvent.getVenue());
    }
}