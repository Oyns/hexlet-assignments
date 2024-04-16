package exercise.controller;

import exercise.dto.GuestCreateDTO;
import exercise.dto.GuestDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.GuestMapper;
import exercise.repository.GuestRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guests")
@RequiredArgsConstructor
public class GuestsController {

    private final GuestRepository guestRepository;

    private final GuestMapper guestMapper;

    @GetMapping(path = "")
    public List<GuestDTO> index() {
        var products = guestRepository.findAll();
        return products.stream()
                .map(guestMapper::map)
                .toList();
    }

    @GetMapping(path = "/{id}")
    public GuestDTO show(@PathVariable long id) {

        var guest = guestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guest with id " + id + " not found"));
        return guestMapper.map(guest);
    }

    // BEGIN
    @PostMapping
    public ResponseEntity<GuestDTO> create(@RequestBody @Valid GuestCreateDTO guestCreateDTO) {
        var guest = guestMapper.map(guestCreateDTO);
        guest = guestRepository.save(guest);
        return new ResponseEntity<>(guestMapper.map(guest), HttpStatus.CREATED);
    }
    // END
}
