package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Weapon {
    private int id;
    private String name;
    private String type;
    private double reload_cd;
    private int cost;
}
