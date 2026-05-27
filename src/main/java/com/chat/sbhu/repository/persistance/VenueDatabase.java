package com.chat.sbhu.repository.persistance;

import com.chat.sbhu.models.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VenueDatabase extends JpaRepository<Venue, UUID> {
}
