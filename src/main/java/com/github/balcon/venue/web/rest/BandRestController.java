package com.github.balcon.venue.web.rest;

import com.github.balcon.venue.dto.BandReadDto;
import com.github.balcon.venue.dto.BandWriteDto;
import com.github.balcon.venue.service.BandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.github.balcon.venue.config.ApplicationConfig.REST_API;

@RestController
@RequestMapping(BandRestController.URI)
@RequiredArgsConstructor
public class BandRestController {
    public static final String URI = REST_API + "/bands";
    private final BandService service;

    @GetMapping
    public List<BandReadDto> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public BandReadDto getById(@PathVariable("id") int id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BandReadDto create(@RequestBody @Valid BandWriteDto band) {
        return service.save(band);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") int id, @RequestBody @Valid BandWriteDto band) {
        service.update(id, band);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        service.delete(id);
    }
}
