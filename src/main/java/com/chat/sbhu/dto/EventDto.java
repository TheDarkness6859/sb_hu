package com.chat.sbhu.dto;

import com.chat.sbhu.models.enums.City;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventDto (UUID id, String name, String venueName, LocalDateTime date, City city) {}
