package com.chat.sbhu.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    private UUID id;
    private String name;
    private Venue venue;
    private String type;

}
