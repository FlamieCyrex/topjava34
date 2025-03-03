package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {

    public static final int MEAL_ID_FIRST = START_SEQ + 3;
    public static final int MEAL_ID_SECOND = START_SEQ + 4;
    public static final int MEAL_ID_THIRD = START_SEQ + 5;
    public static final int MEAL_NULL = 5;
    public static final LocalDate TEST_START_DATE = LocalDate.of(2018, Month.JANUARY, 1);
    public static final LocalDate TEST_END_DATE = LocalDate.of(2021, Month.FEBRUARY, 1);

    public static final Meal meal_first = new Meal(MEAL_ID_FIRST, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0),"Завтрак",1000);
    public static final Meal meal_second = new Meal(MEAL_ID_SECOND, LocalDateTime.of(2023, Month.JUNE, 21, 7, 40),"Обед",2000);
    public static final Meal meal_third = new Meal(MEAL_ID_THIRD, LocalDateTime.of(2025, Month.MARCH, 2, 11, 2),"Ужин",1500);

    public static Meal getNewMeal(){
        return new Meal(LocalDateTime.of(2017, Month.FEBRUARY, 2, 11, 2),"Завтрак",3000);
    }

    public static Meal getMealUpdated(){
        Meal updated = new Meal(meal_first);
        updated.setDateTime(LocalDateTime.of(2017, Month.FEBRUARY, 2, 11, 2));
        updated.setCalories(4000);
        updated.setDescription("Updated description");
        return updated;
    }
    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualTo(expected);
    }
    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }
    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }

}
