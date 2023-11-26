package com.github.balcon.venue.web;

import com.github.balcon.venue.dto.MusicianWriteDto;
import com.github.balcon.venue.service.BandService;
import com.github.balcon.venue.service.MusicianService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/musicians")
@RequiredArgsConstructor
public class MusicianController {
    private final MusicianService service;
    private final BandService bandService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("musicians", service.findAll());
        model.addAttribute("bands", bandService.findAll());
        return "musicians";
    }

    @PostMapping
    public String create(MusicianWriteDto dto) {
        service.save(dto);
        return "redirect:/musicians";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        service.delete(id);
        return "redirect:/musicians";
    }
}
