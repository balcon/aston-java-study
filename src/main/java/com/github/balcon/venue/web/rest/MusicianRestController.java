package com.github.balcon.venue.web.rest;

import com.github.balcon.venue.dto.MusicianReadDto;
import com.github.balcon.venue.dto.MusicianWriteDto;
import com.github.balcon.venue.service.MusicianService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.github.balcon.venue.config.ApplicationConfig.REST_API;

@RestController
@RequestMapping(MusicianRestController.URI)
@RequiredArgsConstructor
public class MusicianRestController {
    public static final String URI = REST_API + "/musicians";
    private final MusicianService service;

    @GetMapping
    public List<MusicianReadDto> getAll(@RequestParam("name") Optional<String> name) {
        return name.isPresent()
                ? service.findByName(name.get())
                : service.findAll();
    }

    @GetMapping("/{id}")
    public MusicianReadDto getById(@PathVariable("id") int id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MusicianReadDto create(@RequestBody @Valid MusicianWriteDto musician) {
        return service.save(musician);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") int id, @RequestBody @Valid MusicianWriteDto musician) {
        service.update(id, musician);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        service.delete(id);
    }
}
