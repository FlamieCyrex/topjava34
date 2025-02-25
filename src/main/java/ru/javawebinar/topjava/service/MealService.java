package ru.javawebinar.topjava.service;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal add(Meal meal, int userId) {
        repository.save(meal, userId);
        return meal;
    }

    public void delete(int mealId, int userId) {
        checkNotFound(repository.delete(mealId, userId), mealId);
    }

    public Meal get(int mealId, int userId) {
        return checkNotFound(repository.get(mealId, userId), mealId);
    }

    public List<Meal> getAll(int userId) {
        return new ArrayList<>(repository.getAll(userId));
    }

    public void update(Meal meal, int userId) {
        checkNotFound(repository.save(meal, userId), meal.getId());
    }
    public List<Meal> getBetween(@Nullable LocalDateTime start, @Nullable LocalDateTime end,int mealId) {
    return new ArrayList<>(repository.getBetween(start, end, mealId));
    }

}