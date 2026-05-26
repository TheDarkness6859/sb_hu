package com.chat.sbhu.controllers;

import com.chat.sbhu.models.Venue;
import com.chat.sbhu.service.VenueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/venue")
@Tag(name = "Venue", description = "Venue controller to management")
public class VenueController {

    private final VenueService service;

    public VenueController (VenueService service){
        this.service = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get all venues in the platform",
            description = "Return a list of all Venues in the platform"
    )
    @ApiResponse(responseCode = "200", description = "List of venue get correctly")
    public ResponseEntity<List<Venue>> getAll () {

        return ResponseEntity.ok(service.getAll());

    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get specific Venue",
            description = "Return a specific Venue through id"
    )
    @ApiResponse(responseCode = "200", description = "Venues recovery correctly")
    public ResponseEntity<Venue> getById (@PathVariable UUID id){

        return ResponseEntity.ok(service.getById(id));

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create Venue",
            description = "Create Venue for the platform"
    )
    @ApiResponse(responseCode = "201", description = "Venue created correctly")
    public ResponseEntity<Venue> createVenue (@RequestBody Venue venue){

        service.add(venue);
        return new ResponseEntity<>(venue, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Modify Venue",
            description = "Edit speficif Venue in the platform"
    )
    @ApiResponse(responseCode = "200", description = "Venue modify correctly")
    public ResponseEntity<Boolean> editVenue (@PathVariable UUID id, @RequestBody Venue venue){

        return ResponseEntity.ok(service.edit(id, venue));

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Delete Venue",
            description = "Delete specific Venue throuhg id"
    )
    @ApiResponse(responseCode = "204", description = "Venue deleted correctly")
    public ResponseEntity<Void> deleteVenue (@PathVariable UUID id){

        boolean deleted = service.delete(id);

        if (deleted){

            return ResponseEntity.noContent().build();

        }else {

            return ResponseEntity.notFound().build();

        }

    }

}
