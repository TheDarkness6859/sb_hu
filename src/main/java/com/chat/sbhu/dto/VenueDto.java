package com.chat.sbhu.dto;

import com.chat.sbhu.models.Event;
import com.chat.sbhu.models.enums.City;

import java.util.UUID;

public record VenueDto (UUID id, String name, String address, Event event, Integer capacity, City city){
}
