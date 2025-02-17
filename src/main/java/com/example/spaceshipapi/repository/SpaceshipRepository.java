package com.example.spaceshipapi.repository;

import com.example.spaceshipapi.model.Spaceship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpaceshipRepository extends JpaRepository<Spaceship, Integer> {
    List<Spaceship> findByNameContaining(String name);
}

