package com.web.web.respository;

import com.web.web.models.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClubRepository extends JpaRepository<Club, Long> {
    Optional<Club> findByTitle(String url);
    @Query("SELECT c from Club c WHERE c.title LIKE concat('%', :query,'%') ")
    List<Club> searchClub(String query);
}
