package ru.vkokourov.goodish.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Dish {

    private String name;
    private double calories;
    private double proteins;
    private double fats;
    private double carbs;
}
