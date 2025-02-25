package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Meal> mealsMap = new ConcurrentHashMap<>();
    private final AtomicInteger mealIdCounter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(mealIdCounter.incrementAndGet());
            meal.setUserId(userId);
            mealsMap.put(meal.getId(), meal);
            log.info("Saved meal: {}", meal);
            return meal;
        }
        // handle case: update, but not present in storage
        if (checkUserId(meal.getId(), userId)) {
            mealsMap.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        if(checkUserId(id, userId)) {
            return mealsMap.remove(id) != null;
        }
        return false ;
    }

    @Override
    public Meal get(int id, int userId) {
        return checkUserId(id, userId) ? mealsMap.get(id) : null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return mealsMap.values()
                .stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getBetween(@Nullable LocalDateTime start, @Nullable LocalDateTime end, int userId) {
        return getAll(userId).stream()
                .filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDateTime(),start,end))
                .collect(Collectors.toList());
    }

    private boolean checkUserId (int mealId, int userId) {
        return mealsMap.get(mealId).getUserId().equals(userId);
    }
}

