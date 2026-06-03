package com.chat.sbhu.repository;

import com.chat.sbhu.dto.EventDto;
import com.chat.sbhu.models.Event;
import com.chat.sbhu.models.enums.City;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

    @Query("SELECT new com.chat.sbhu.dto.EventDto(e.id, e.name, v.name, e.date, v.city) " +
            "FROM Event e JOIN e.venue v " +
            "ORDER BY e.date DESC")
    Slice<EventDto> findEventCatalog(Pageable pageable);

    @Query("SELECT new com.chat.sbhu.dto.EventDto(e.id, e.name, v.name, e.date, v.city) " +
            "FROM Event e JOIN e.venue v " +
            "WHERE (:name IS NULL OR LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:city IS NULL OR v.city = :city) " +
            "ORDER BY e.date DESC")
    Slice<EventDto> findAdvancedEvents(
            @Param("name") String name,
            @Param("city") City city,
            Pageable pageable
    );

}
