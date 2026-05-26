package com.chat.sbhu.dto;

import com.chat.sbhu.models.Venue;

import java.util.UUID;

public record EventDto (UUID id, String name, Venue venue, String type) {}
