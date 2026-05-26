package com.chat.sbhu.controllers;

import com.chat.sbhu.models.Event;
import com.chat.sbhu.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/events")
@Tag(name = "Events", description = "Controller to manage Events in the platform")
public class EventController {

    private final EventService service;

    public EventController (EventService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get all Events in the platform",
            description = "Return a list of all Events in the platform"
    )
    @ApiResponse(responseCode = "200", description = "List of Events recovery correctly")
    public ResponseEntity<List<Event>> getEvents (){

        return ResponseEntity.ok(service.getAll());

    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get specific Event through id",
            description = "Find the specific Event in our platform through id"
    )
    @ApiResponse(responseCode = "200", description = "Event recovery correctly")
    public ResponseEntity<Event> getById(@PathVariable UUID id){

        return ResponseEntity.ok(service.getById(id));

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "register new Events.",
            description = "Save a new Event in the platform."
    )
    @ApiResponse(responseCode = "201", description = "Event created correctly")
    public ResponseEntity<Event> addEvent (@RequestBody Event event){

        service.add(event);
        return new ResponseEntity<>(event, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Modify Event",
            description = "Allow modify Event through id"
    )
    @ApiResponse(responseCode = "200", description = "Event modify correctly")
    public ResponseEntity<Boolean> edit (@PathVariable UUID id, @RequestBody Event event){

        return ResponseEntity.ok(service.edit(id, event));

    }

}
