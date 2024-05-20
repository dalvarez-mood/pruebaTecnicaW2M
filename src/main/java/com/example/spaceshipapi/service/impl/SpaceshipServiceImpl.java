package com.example.spaceshipapi.service.impl;

import com.example.spaceshipapi.dto.SpaceshipDto;
import com.example.spaceshipapi.mapper.SpaceshipMapper;
import com.example.spaceshipapi.model.Spaceship;
import com.example.spaceshipapi.repository.SpaceshipRepository;
import com.example.spaceshipapi.service.SpaceshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SpaceshipServiceImpl implements SpaceshipService {

    @Autowired
    private SpaceshipRepository spaceshipRepository;

    @Autowired
    private SpaceshipMapper mapper;

    @Override
    public Page<SpaceshipDto> getAllSpaceships(Pageable pageable) {
        return toPageSpaceshipDto(spaceshipRepository.findAll(pageable));
    }

    @Override
    public Optional<Spaceship> getSpaceshipById(Integer id) {
        return spaceshipRepository.findById(id);
    }

    @Override
    @Cacheable("spaceshipsByName")
    public List<SpaceshipDto> getSpaceshipsByName(String name) {
        List<Spaceship> list = spaceshipRepository.findByNameContaining(name);
        return list.stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public SpaceshipDto createSpaceship(SpaceshipDto spaceshipDto) {
        Spaceship spaceship = spaceshipRepository.save(mapper.reverseMap(spaceshipDto));
        return mapper.map(spaceship);
    }

    @Override
    public SpaceshipDto updateSpaceship(Integer id, SpaceshipDto spaceship) {

        return spaceshipRepository.findById(id)
                .map(existingSpaceship -> {
                    existingSpaceship.setName(spaceship.getName());
                    existingSpaceship.setSeriesOrMovie(spaceship.getSeriesOrMovie());
                    return mapper.map(spaceshipRepository.save(existingSpaceship));
                })
                .orElseThrow(() -> new RuntimeException("Spaceship not found"));
    }

    @Override
    public void deleteSpaceship(Integer id) {
        spaceshipRepository.deleteById(id);
    }


    private Page<SpaceshipDto> toPageSpaceshipDto(Page<Spaceship> objects) {
        Page<SpaceshipDto> dtos  = objects.map(this::convertToSpaceshipDto);
        return dtos;
    }

    private SpaceshipDto convertToSpaceshipDto(Spaceship spaceship) {
        return mapper.map(spaceship);
    }
}
