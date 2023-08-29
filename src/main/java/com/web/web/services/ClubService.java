package com.web.web.services;

import com.web.web.dto.ClubDTO;
import com.web.web.models.Club;
import jakarta.validation.Valid;

import java.util.List;

public interface ClubService {
    List<ClubDTO> findAllClub();
    Club saveClub(ClubDTO clubDTO);
    ClubDTO findClubById(Long clubID);

    void updateClub(ClubDTO club);

    void delete(Long clubID);

    List<ClubDTO> searchClub(String query);
}
