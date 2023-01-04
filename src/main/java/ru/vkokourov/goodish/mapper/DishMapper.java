package ru.vkokourov.goodish.mapper;

import ru.vkokourov.goodish.model.Dish;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DishMapper {

    public Dish toDish(String str) {
        String[] arr = str.split(",");
        return new Dish(
                arr[0],
                Double.parseDouble(arr[1]),
                Double.parseDouble(arr[2]),
                Double.parseDouble(arr[3]),
                Double.parseDouble(arr[4]));
    }

    public String toString(Dish dish) {
        return  dish.getName() +
                "," +
                dish.getCalories() +
                "," +
                dish.getProteins() +
                "," +
                dish.getFats() +
                "," +
                dish.getCarbs()
                ;
    }

    public String toString(List<Dish> dishes) {
        StringBuilder sb = new StringBuilder();
        for (Dish dish : dishes) {
            sb.append(toString(dish));
        }
        return sb.toString();
    }

    public List<Dish> toDishList(List<String> strings) {
        return new ArrayList<>(
                strings.stream()
                        .map(this::toDish)
                        .collect(Collectors.toList())
        );
    }
}
