package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("guest")
public class GuestController extends BaseRestController implements GuestAPI {

    private final GuestService service;

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody GuestDTO guestDTO) {
        return ResponseEntity.created(locationByEntity(service.add(guestDTO).getId())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuestDTO> findById(@PathVariable(value = "id") Long guestId) {
        return ResponseEntity.ok(service.findById(guestId));
    }

    @GetMapping
    public ResponseEntity<List<GuestDTO>> listAllGuests() {
        return ResponseEntity.ok(service.list());
    }

    @GetMapping(params = "guestName")
    public ResponseEntity<Guest> findByName(String guestName) {
        return ResponseEntity.ok(service.findByName(guestName));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Long guestId) {
        service.delete(guestId);
        return ResponseEntity.ok("Guest deleted successfully.");
    }

    @PutMapping
    public ResponseEntity<GuestDTO> update(@RequestBody GuestDTO guestDTO) {
        return ResponseEntity.ok(service.update(guestDTO));
    }

}