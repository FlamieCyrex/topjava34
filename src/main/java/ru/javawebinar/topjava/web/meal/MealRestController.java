package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MealService service;

    public List<MealTo> getAll() {
        log.info("Getting all meals");
        return MealsUtil.getTos(service.getAll(SecurityUtil.getAuthUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public List<MealTo> getBetween(@Nullable LocalDateTime start, @Nullable LocalDateTime end) {
        log.info("Getting between meals");
        return MealsUtil.getFilteredTos(service.getAll(SecurityUtil.getAuthUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY, start, end);
    }

    public Meal get(int mealId) {
        return service.get(mealId, SecurityUtil.getAuthUserId());
    }

    public Meal create(Meal meal) {
        return service.add(meal, SecurityUtil.getAuthUserId());
    }

    public void update(Meal meal, int userId) {
        service.update(meal, userId);
    }

    public void delete(int mealId) {
        service.delete(mealId, SecurityUtil.getAuthUserId());
    }
}