package com.chat.sbhu.dto;

import com.chat.sbhu.models.Event;

import java.util.UUID;

public record VenueDto (UUID id, String name, String address, Event event, Integer capacity){
}
