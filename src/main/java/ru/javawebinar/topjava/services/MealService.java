package ru.javawebinar.topjava.services;

import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.ObjectRepository;

import java.util.List;

public class MealService {
    private final ObjectRepository<MealTo, Integer> mealRepository;

    public MealService(ObjectRepository<MealTo, Integer> mealRepository) {
        this.mealRepository = mealRepository;
    }
    public MealTo getMeal(int id) {
        return mealRepository.findById(id);
    }
    public void saveMeal(MealTo meal) {
        mealRepository.save(meal);
    }
    public void deleteMeal(MealTo meal) {
        mealRepository.delete(meal);
    }
    public void updateMeal(MealTo meal) {
        mealRepository.update(meal);
    }
    public List<MealTo> getMeals() {
        return mealRepository.findAll();
    }
}
