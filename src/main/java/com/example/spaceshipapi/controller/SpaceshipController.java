package com.example.spaceshipapi.controller;

import com.example.spaceshipapi.dto.SpaceshipDto;
import com.example.spaceshipapi.model.Spaceship;
import com.example.spaceshipapi.service.SpaceshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spaceships")
public class SpaceshipController {

    @Autowired
    private SpaceshipService spaceshipService;

    @GetMapping
    public Page<SpaceshipDto> getAllSpaceships(Pageable pageable) {
        return spaceshipService.getAllSpaceships(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Spaceship> getSpaceshipById(@PathVariable Integer id) {
        return spaceshipService.getSpaceshipById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<SpaceshipDto> getSpaceshipsByName(@RequestParam String name) {
        return spaceshipService.getSpaceshipsByName(name);
    }

    @PostMapping
    public SpaceshipDto createSpaceship(@RequestBody SpaceshipDto spaceship) {
        return spaceshipService.createSpaceship(spaceship);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpaceshipDto> updateSpaceship(@PathVariable Integer id, @RequestBody SpaceshipDto spaceship) {
        try {
            return ResponseEntity.ok(spaceshipService.updateSpaceship(id, spaceship));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpaceship(@PathVariable Integer id) {
        spaceshipService.deleteSpaceship(id);
        return ResponseEntity.noContent().build();
    }
}
