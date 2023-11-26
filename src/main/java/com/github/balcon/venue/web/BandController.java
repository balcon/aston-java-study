package com.github.balcon.venue.web;

import com.github.balcon.venue.dto.BandWriteDto;
import com.github.balcon.venue.service.BandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bands")
@RequiredArgsConstructor
public class BandController {
    private final BandService service;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("bands", service.findAll());
        return "bands";
    }

    @PostMapping
    public String create(BandWriteDto band) {
        service.save(band);
        return "redirect:/bands";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        service.delete(id);
        return "redirect:/bands";
    }
}
