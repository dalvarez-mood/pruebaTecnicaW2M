package com.example.spaceshipapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpaceshipDto {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    @NonNull
    private String name;

    @JsonProperty("seriesOrMovie")
    @NonNull
    private String seriesOrMovie;
}
