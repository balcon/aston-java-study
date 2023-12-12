package com.github.balcon.venue.web.rest;

import static com.github.balcon.venue.config.ApplicationConfig.REST_API;

import com.github.balcon.venue.dto.BandReadDto;
import com.github.balcon.venue.dto.BandWriteDto;
import com.github.balcon.venue.service.BandService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;



/**
 * Rest controller for {@link com.github.balcon.venue.entity.Band}.
 *
 * @author Konstantin Balykov
 */

@RestController
@RequestMapping(REST_API + "/bands")
@RequiredArgsConstructor
public class BandRestController {
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

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  public void delete(@PathVariable("id") int id) {
    service.delete(id);
  }
}
