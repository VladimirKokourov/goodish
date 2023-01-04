package ru.vkokourov.goodish.repository;

import lombok.AllArgsConstructor;
import ru.vkokourov.goodish.mapper.DishMapper;
import ru.vkokourov.goodish.model.Dish;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class DishRepository {

    private final File file;
    private final DishMapper dishMapper;

    private void saveAll(List<Dish> dishList) {
        String result = dishMapper.toString(dishList);
        try {
            Files.writeString(file.toPath(), result, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Dish> findAll() {
        List<String> list = new ArrayList<>();

        try {
            list = Files.readAllLines(file.toPath(), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dishMapper.toDishList(list);
    }

    public Dish save(Dish dish) {
        List<String> stringList = new ArrayList<>();
        try {
            stringList = Files.readAllLines(file.toPath(), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Dish> dishList = dishMapper.toDishList(stringList);
        dishList.add(dish);
        saveAll(dishList);

        return dish;
    }

    public Optional<Dish> findByName(String name) {
        List<Dish> dishList = findAll();

        for (Dish dish : dishList) {
            if (dish.getName().equals(name)) {
                return Optional.of(new Dish(dish.getName(), dish.getCalories(), dish.getProteins(),
                        dish.getFats(), dish.getCarbs()));
            }
        }
        return Optional.empty();
    }

    public boolean deleteByName(String name) {
        boolean result;
        List<Dish> dishList = findAll();
        result = dishList.removeIf(dish -> dish.getName().equals(name));
        saveAll(dishList);
        return result;
    }
}
