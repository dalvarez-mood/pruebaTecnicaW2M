package com.example.spaceshipapi.service;

import com.example.spaceshipapi.dto.SpaceshipDto;
import com.example.spaceshipapi.mapper.SpaceshipMapperImpl;
import com.example.spaceshipapi.model.Spaceship;
import com.example.spaceshipapi.repository.SpaceshipRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SpaceshipServiceTest {

    @Autowired
    private SpaceshipService spaceshipService;

    @MockBean
    private SpaceshipRepository spaceshipRepository;

    @Autowired
    private SpaceshipMapperImpl mapper;


    @Test
    public void testGetSpaceshipById() {
        Spaceship spaceship = new Spaceship(1, "Millennium Falcon", "Star Wars");
        when(spaceshipRepository.findById(1)).thenReturn(Optional.of(spaceship));

        Optional<Spaceship> found = spaceshipService.getSpaceshipById(1);
        assertTrue(found.isPresent());
        assertEquals(spaceship.getName(), found.get().getName());
    }

    @Test
    public void testGetAllSpaceships(){
        // Datos de prueba
        Spaceship spaceship1 = new Spaceship(1, "Millennium Falcon", "Star Wars");
        Spaceship spaceship2 = new Spaceship(2, "X-Wing", "Star Wars");

        SpaceshipDto spaceshipDto1 = new SpaceshipDto(1, "Millennium Falcon", "Star Wars");
        SpaceshipDto spaceshipDto2 = new SpaceshipDto(2, "X-Wing", "Star Wars");

        List<Spaceship> spaceshipList = Arrays.asList(spaceship1, spaceship2);
        Page<Spaceship> spaceshipPage = new PageImpl<>(spaceshipList);

        Pageable pageable = PageRequest.of(0, 10);

        // Mockear el repositorio y el mapper
        when(spaceshipRepository.findAll(pageable)).thenReturn(spaceshipPage);

        // Llamar al método a testear
        Page<SpaceshipDto> result = spaceshipService.getAllSpaceships(pageable);

        // Verificaciones
        assertEquals(2, result.getTotalElements());
        assertEquals("Millennium Falcon", result.getContent().get(0).getName());
        assertEquals("X-Wing", result.getContent().get(1).getName());
    }

    @Test
    public void testUpdateSpaceship(){
        Spaceship spaceship1 = new Spaceship(1, "Millennium Falcon", "Star Wars");
        Spaceship spaceship2 = new Spaceship(2, "X-Wing", "Star Wars");
        SpaceshipDto spaceshipDto2 = new SpaceshipDto(1, "X-Wing", "Star Wars");

        when(spaceshipRepository.findById(1)).thenReturn(Optional.of(spaceship1));
        when(spaceshipRepository.save(any())).thenReturn(spaceship1);
        SpaceshipDto spaceshipDto = spaceshipService.updateSpaceship(1, spaceshipDto2);
        assertEquals(spaceshipDto2, spaceshipDto);
    }

    @Test
    public void testCreateSpaceship(){
        Spaceship spaceship1 = new Spaceship(1, "Millennium Falcon", "Star Wars");
        SpaceshipDto spaceshipDto1 = new SpaceshipDto(1, "Millennium Falcon", "Star Wars");
        when(spaceshipRepository.save(any())).thenReturn(spaceship1);
        SpaceshipDto spaceship = spaceshipService.createSpaceship(spaceshipDto1);
        assertEquals(spaceship,spaceshipDto1);
    }

    @Test
    public void testGetSpaceshipsByName_withCache() {
        // Datos de prueba
        Spaceship spaceship1 = new Spaceship(1, "Millennium Falcon", "Star Wars");
        Spaceship spaceship2 = new Spaceship(2, "X-Wing", "Star Wars");

        SpaceshipDto spaceshipDto1 = new SpaceshipDto(1, "Millennium Falcon", "Star Wars");
        SpaceshipDto spaceshipDto2 = new SpaceshipDto(2, "X-Wing", "Star Wars");

        List<Spaceship> spaceshipList = Arrays.asList(spaceship1, spaceship2);

        // Mockear el repositorio y el mapper
        when(spaceshipRepository.findByNameContaining("Star")).thenReturn(spaceshipList);

        // Primera llamada para llenar el caché
        List<SpaceshipDto> result1 = spaceshipService.getSpaceshipsByName("Star");

        // Verificaciones de la primera llamada
        assertEquals(2, result1.size());
        assertEquals("Millennium Falcon", result1.get(0).getName());
        assertEquals("X-Wing", result1.get(1).getName());
        verify(spaceshipRepository, times(1)).findByNameContaining("Star");

    }

    @Test
    public void testDelete(){
        spaceshipService.deleteSpaceship(1);
        verify(spaceshipRepository, times(1)).deleteById(1);
    }
}