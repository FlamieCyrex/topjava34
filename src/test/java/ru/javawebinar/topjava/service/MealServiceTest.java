package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({"classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService mealService;

    @Test
    public void get() {
        Meal meal = mealService.get(MEAL_ID_SECOND, ADMIN_ID);
        assertMatch(meal, meal_second);
    }

    @Test
    public void getIfWrongUserId() {
        assertThrows(NotFoundException.class, () -> mealService.get(MEAL_ID_SECOND, 1));
    }

    @Test
    public void delete() {
        mealService.delete(MEAL_ID_SECOND, ADMIN_ID);
        assertThrows(NotFoundException.class, () -> mealService.delete(MEAL_ID_SECOND, ADMIN_ID));
    }

    @Test
    public void deleteIfNotFound() {
        assertThrows(NotFoundException.class, () -> mealService.delete(NOT_FOUND, MEAL_NULL));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> mealsUser = mealService.getBetweenInclusive(TEST_START_DATE, TEST_END_DATE, USER_ID);
        List<Meal> mealsAdmin = mealService.getBetweenInclusive(TEST_START_DATE, TEST_END_DATE, ADMIN_ID);
        assertMatch(mealsUser, meal_first);
        assertMatch(mealsAdmin, new ArrayList<Meal>() {
        });
    }

    @Test
    public void getAll() {
        List<Meal> meals = mealService.getAll(USER_ID);
        assertMatch(meals, meal_first);
    }

    @Test
    public void update() {
        Meal updated = getMealUpdated();
        mealService.update(updated, USER_ID);
        assertMatch(mealService.get(MEAL_ID_FIRST, USER_ID), updated);
    }

    @Test
    public void updateIfNotFound() {
        assertThrows(NotFoundException.class, () -> mealService.update(getMealUpdated(), ADMIN_ID));
    }

    @Test
    public void create() {
        Meal createdMeal = mealService.create(getNewMeal(), USER_ID);
        Integer newId = createdMeal.getId();
        Meal newMeal = getNewMeal();
        newMeal.setId(newId);
        assertMatch(createdMeal, newMeal);
        assertMatch(mealService.get(newId, USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () -> mealService.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Заа", 2000), USER_ID));
    }
}