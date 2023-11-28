package com.github.balcon.venue.web.rest;

import com.github.balcon.venue.dto.EventReadDto;
import com.github.balcon.venue.dto.EventWriteDto;
import com.github.balcon.venue.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.github.balcon.venue.config.ApplicationConfig.REST_API;

@RestController
@RequestMapping(REST_API + "/events")
@RequiredArgsConstructor
public class EventRestController {
    private final EventService service;

    @GetMapping
    public List<EventReadDto> getAll(@RequestParam("date") Optional<LocalDate> date) {
        return date.isPresent()
                ? service.findByDate(date.get())
                : service.findAll();
    }

    @GetMapping("/{id}")
    public EventReadDto getById(@PathVariable("id") int id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventReadDto create(@RequestBody @Valid EventWriteDto event) {
        return service.save(event);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") int id, @RequestBody @Valid EventWriteDto event) {
        service.update(id, event);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        service.delete(id);
    }

    @PutMapping("/{eventId}/bands/{bandId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBand(@PathVariable("eventId") int eventId, @PathVariable("bandId") int bandId) {
        service.addBand(eventId, bandId);
    }

    @DeleteMapping("/{eventId}/bands/{bandId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBand(@PathVariable("eventId") int eventId, @PathVariable("bandId") int bandId) {
        service.removeBand(eventId, bandId);
    }
}
