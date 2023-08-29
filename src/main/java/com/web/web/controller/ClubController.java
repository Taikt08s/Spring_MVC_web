package com.web.web.controller;

import com.web.web.dto.ClubDTO;
import com.web.web.models.Club;
import com.web.web.models.UserEntity;
import com.web.web.security.SecurityUtil;
import com.web.web.services.ClubService;

import java.util.List;

import com.web.web.services.UserService;
import jakarta.enterprise.inject.build.compatible.spi.Validation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ClubController {

    private ClubService clubService;
    private UserService userService;

    @Autowired
    public ClubController(ClubService clubService, UserService userService) {
        this.clubService = clubService;
        this.userService = userService;
    }

    @GetMapping("/clubs")
    public String listClubs(Model model) {
        UserEntity user= new UserEntity();
        List<ClubDTO> clubs = clubService.findAllClub();
        String username= SecurityUtil.getSessionUser();
        if(username!=null){
            user=userService.findByUserName(username);
            model.addAttribute("user",user);
        }
        model.addAttribute("user",user);
        model.addAttribute("clubs", clubs);
        return "club-list";
    }

    @GetMapping("/clubs/search")
    public String searchClub(@RequestParam(value = "query") String query, Model model) {
        List<ClubDTO> clubs = clubService.searchClub(query);
        model.addAttribute("clubs", clubs);
        return "club-list";
    }

    @GetMapping("/clubs/create")
    public String createClubForm(Model model) {
        Club club = new Club();
        model.addAttribute("club", club);
        return "club-create";
    }

    @PostMapping("/clubs/create")
    public String saveClub(@Valid @ModelAttribute("club") ClubDTO clubDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("club", clubDTO);
            return "club-create";
        }
        clubService.saveClub(clubDTO);
        return "redirect:/clubs";
    }

    @GetMapping("/clubs/{clubID}/edit")
    public String editClubForm(@PathVariable("clubID") Long clubID, Model model) {
        ClubDTO club = clubService.findClubById(clubID);
        model.addAttribute("club", club);
        return "club-edit";
    }

    @PostMapping("/clubs/{clubID}/edit")
    public String updateClub(@PathVariable("clubID") Long clubID,
                             @Valid @ModelAttribute("club") ClubDTO club,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("club", club);
        }
        club.setId(clubID);
        clubService.updateClub(club);
        return "redirect:/clubs";
    }

    @GetMapping("/clubs/{clubID}")
    public String clubDetail(@PathVariable("clubID") Long clubID, Model model) {
        UserEntity user= new UserEntity();
        ClubDTO clubDTO = clubService.findClubById(clubID);
        String username= SecurityUtil.getSessionUser();
        if(username!=null){
            user=userService.findByUserName(username);
            model.addAttribute("user",user);
        }
        model.addAttribute("user",user);
        model.addAttribute("club", clubDTO);
        return "clubs-detail";
    }

    @GetMapping("/clubs/{clubID}/delete")
    public String deleteClub(@PathVariable("clubID") Long clubID) {
        clubService.delete(clubID);
        return "redirect:/clubs";
    }
}
