package com.web.web.services.impl;

import com.web.web.dto.ClubDTO;
import com.web.web.mapper.ClubMapper;
import com.web.web.models.Club;
import com.web.web.models.UserEntity;
import com.web.web.respository.ClubRepository;
import com.web.web.respository.UserRepository;
import com.web.web.security.SecurityUtil;
import com.web.web.services.ClubService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.web.web.mapper.ClubMapper.mapToClub;
import static com.web.web.mapper.ClubMapper.mapToClubDTO;

@Service
public class ClubImplement implements ClubService {

    private ClubRepository clubRepository;
    private UserRepository userRepository;

    @Autowired
    public ClubImplement(ClubRepository clubRepository, UserRepository userRepository) {
        this.clubRepository = clubRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ClubDTO> findAllClub() {
        List<Club> clubs = clubRepository.findAll();
        return clubs.stream().map((club) -> mapToClubDTO(club)).collect(Collectors.toList());
    }

    @Override
    public Club saveClub(ClubDTO clubDTO) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUserName(username);
        Club club = mapToClub(clubDTO);
        club.setCreatedBy(user);
        return clubRepository.save(club);
    }

    @Override
    public ClubDTO findClubById(Long clubID) {
        Club club = clubRepository.findById(clubID).get();
        return mapToClubDTO(club);
    }

    @Override
    public void updateClub(ClubDTO clubDto) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUserName(username);
        Club club = mapToClub(clubDto);
        club.setCreatedBy(user);
        clubRepository.save(club);
    }

    @Override
    public void delete(Long clubID) {
        clubRepository.deleteById(clubID);
    }

    @Override
    public List<ClubDTO> searchClub(String query) {
        List<Club> clubs = clubRepository.searchClub(query);
        return clubs.stream().map(ClubMapper::mapToClubDTO).collect(Collectors.toList());
    }

}
