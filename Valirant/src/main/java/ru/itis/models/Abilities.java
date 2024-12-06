package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Abilities {
    private int id;
    private String name;
    private int agent_id;
    private String cooldown;
    private String description;
}
