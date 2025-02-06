package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

//        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles

        Map<Integer, Integer> map = new HashMap<>();
        List<UserMealWithExcess> list = new ArrayList<>(meals.toArray().length);
        for (UserMeal meal : meals) {
            int dayOfMonth = meal.getDateTime().getDayOfMonth();
            map.merge(dayOfMonth, meal.getCalories(), Integer::sum);


        }
        for (UserMeal meal : meals) {
            boolean excess = map.get(meal.getDateTime().getDayOfMonth()) > caloriesPerDay;
            LocalTime localTime = meal.getDateTime().toLocalTime();
            if (localTime.isAfter(startTime) && localTime.isBefore(endTime)) {
                list.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                        excess));
            }
        }
        return list;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<Integer, Integer> map = meals.stream()
                .collect(Collectors.groupingBy((UserMeal meal) -> meal.getDateTime().getDayOfMonth(), Collectors.summingInt(UserMeal::getCalories)
                ));

        List<UserMealWithExcess> userMealWithExcessList = (List<UserMealWithExcess>) meals.stream()
                .filter(x -> TimeUtil.isBetweenHalfOpen(x.getDateTime().toLocalTime(), startTime, endTime))
                .map(x -> new UserMealWithExcess(
                        x.getDateTime(),
                        x.getDescription(),
                        x.getCalories(),
                        map.get(x.getDateTime().getDayOfMonth()) > caloriesPerDay))
                .collect(Collectors.toList());

        return userMealWithExcessList;
    }
}
