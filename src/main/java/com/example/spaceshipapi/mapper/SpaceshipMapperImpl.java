package com.example.spaceshipapi.mapper;

import com.example.spaceshipapi.dto.SpaceshipDto;
import com.example.spaceshipapi.model.Spaceship;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SpaceshipMapperImpl implements SpaceshipMapper {

    @Override
    public SpaceshipDto map(Spaceship source) {
        SpaceshipDto dto = new SpaceshipDto();
        dto.setId(source.getId());
        dto.setName(source.getName());
        dto.setSeriesOrMovie(source.getSeriesOrMovie());
        return dto;
    }

    @Override
    public Spaceship reverseMap(SpaceshipDto source) {
        Spaceship spaceship = new Spaceship();
        spaceship.setId(source.getId());
        spaceship.setName(source.getName());
        spaceship.setSeriesOrMovie(source.getSeriesOrMovie());
        return spaceship;
    }


}
