package com.chat.sbhu.controllers;

import com.chat.sbhu.dto.EventDto;
import com.chat.sbhu.models.Event;
import com.chat.sbhu.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            description = "Return the events catalog with pagination and sorting"
    )
    @ApiResponse(responseCode = "200", description = "List of Events recovery correctly")
    public ResponseEntity<Slice<EventDto>> getEvents (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){

        return ResponseEntity.ok(service.getCatalog(page, size));

    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get specific Event through id",
            description = "Find the specific Event in our platform through id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Event recovery correctly"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    public ResponseEntity<EventDto> getById(@PathVariable UUID id){

        EventDto event = service.getById(id);

        if (event == null){

            return ResponseEntity.notFound().build();

        }

        return ResponseEntity.ok(event);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "register new Events.",
            description = "Save a new Event in the platform."
    )
    @ApiResponse(responseCode = "201", description = "Event created correctly")
    public ResponseEntity<EventDto> addEvent (@RequestBody Event event){

        Event e = service.add(event);

        EventDto dto = new EventDto(e.getId(), e.getName(), e.getVenue().getName(), e.getDate(), e.getVenue().getCity());

        return new ResponseEntity<>(dto, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Modify Event",
            description = "Allow modify Event through id"
    )
    @ApiResponse(responseCode = "200", description = "Event modify correctly")
    public ResponseEntity<EventDto> edit (@PathVariable UUID id, @RequestBody Event event){

        Event e = service.edit(id, event);

        EventDto dto = new EventDto(e.getId(), e.getName(), e.getVenue().getName(), e.getDate(), e.getVenue().getCity());

        return ResponseEntity.ok(dto);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete Event",
            description = "Delete a specific Event through id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Deleted Correctly"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    public ResponseEntity<Void> deleteEvent (@PathVariable UUID id){

        boolean deletedEvent = service.delete(id);

        if (deletedEvent){

            return ResponseEntity.noContent().build();

        }else {

            return ResponseEntity.notFound().build();

        }

    }


}
