package com.chat.sbhu.repository;

import com.chat.sbhu.models.Venue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VenueRepository extends JpaRepository<Venue, UUID> {

    Optional<Venue> getByName(String name);

    @Query("SELECT v FROM Venue v")
    Page<Venue> getAllPaginated(Pageable pageable);

}
