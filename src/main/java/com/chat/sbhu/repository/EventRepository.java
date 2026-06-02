package com.chat.sbhu.repository;

import com.chat.sbhu.dto.EventDto;
import com.chat.sbhu.models.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

    @Query("SELECT new com.chat.sbhu.dto.EventDto(e.id, e.name, v.name, e.date, v.city) " +
            "FROM Event e JOIN e.venue v " +
            "ORDER BY e.date DESC")
    Slice<EventDto> findEventCatalog(Pageable pageable);

}
