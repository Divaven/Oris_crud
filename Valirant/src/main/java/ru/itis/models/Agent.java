package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Agent {
    private int id;
    private String name;
    private String role;
    private String description;
    private String difficulty;
}
