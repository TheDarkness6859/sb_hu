package com.chat.sbhu.repository.persistance;

import com.chat.sbhu.models.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventDatabase extends JpaRepository<Event, UUID> {
}
