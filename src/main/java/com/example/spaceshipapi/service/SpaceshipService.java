package com.example.spaceshipapi.service;

import com.example.spaceshipapi.dto.SpaceshipDto;
import com.example.spaceshipapi.model.Spaceship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SpaceshipService {

    Page<SpaceshipDto> getAllSpaceships(Pageable pageable);

    Optional<Spaceship> getSpaceshipById(Integer id);

    List<SpaceshipDto> getSpaceshipsByName(String name);

    SpaceshipDto createSpaceship(SpaceshipDto spaceship);

    SpaceshipDto updateSpaceship(Integer id, SpaceshipDto spaceship);

    void deleteSpaceship(Integer id);
}
