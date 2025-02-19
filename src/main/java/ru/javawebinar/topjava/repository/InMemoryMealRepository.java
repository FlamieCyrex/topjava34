package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.MealTo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryMealRepository implements ObjectRepository<MealTo, Integer> {
    private final HashMap<Integer, MealTo> meals = new HashMap<>();
    private int idCounter = 1;


    @Override
    public void save(MealTo meal) {
    meal.setId(idCounter);
    meals.put(idCounter++, meal);
    }

    @Override
    public MealTo findById(Integer id) {
        return meals.get(id);
    }

    @Override
    public List<MealTo> findAll() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public void delete(MealTo object) {
    meals.remove(object.getId());
    }

    @Override
    public void update(MealTo object) {
    meals.put(object.getId(), object);
    }
}
