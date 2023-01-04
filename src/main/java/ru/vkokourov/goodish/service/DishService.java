package ru.vkokourov.goodish.service;

import lombok.RequiredArgsConstructor;
import ru.vkokourov.goodish.model.Dish;
import ru.vkokourov.goodish.repository.DishRepository;

import java.util.List;

@RequiredArgsConstructor
public class DishService {

    private final DishRepository dishRepository;

    public List<Dish> findAll() {
        return dishRepository.findAll();
    }

    public Dish findByName(String name){
        return dishRepository.findByName(name).
                orElseThrow(() -> new RuntimeException("Dish not found"));
    }

    public void addDish(Dish dish) {
        dishRepository.save(dish);
    }

    public void editDish(Dish dish) {
        dishRepository.deleteByName(dish.getName());
        dishRepository.save(dish);
    }

    public void deleteByName(String name) {
        dishRepository.deleteByName(name);
    }
}
