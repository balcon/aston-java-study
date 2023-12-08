package com.github.balcon.venue.web.rest;

import static com.github.balcon.venue.config.ApplicationConfig.REST_API;

import com.github.balcon.venue.dto.MusicianReadDto;
import com.github.balcon.venue.dto.MusicianWriteDto;
import com.github.balcon.venue.service.MusicianService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for {@link com.github.balcon.venue.entity.Musician}.
 *
 * @author Konstantin Balykov
 */

@RestController
@RequestMapping(REST_API + "/musicians")
@RequiredArgsConstructor
public class MusicianRestController {
  private final MusicianService service;

  /**
   * Get musicians filtered by name.
   */

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
