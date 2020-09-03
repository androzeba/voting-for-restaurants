package ru.internship.voting;

import ru.internship.voting.model.Dish;
import ru.internship.voting.model.Restaurant;

import java.time.LocalDate;
import java.time.LocalTime;

import static ru.internship.voting.model.AbstractBaseEntity.START_SEQ;

public class DishTestData {
    public static TestMatcher<Dish> DISH_MATCHER = TestMatcher.usingFieldsWithIgnoringAssertions(Dish.class, "restaurant");


    public static final int DISH1_ID = START_SEQ + 8;
    public static final int DISH2_ID = START_SEQ + 9;
    public static final int DISH3_ID = START_SEQ + 10;
    public static final int DISH4_ID = START_SEQ + 11;
    public static final int DISH5_ID = START_SEQ + 12;
    public static final int DISH6_ID = START_SEQ + 13;

    public static final Dish DISH1 = new Dish(DISH1_ID, "Салат 1", LocalDate.of(2020,8,1), 50);
    public static final Dish DISH2 = new Dish(DISH2_ID, "Гарнир 1", LocalDate.of(2020,8,1), 50);
    public static final Dish DISH3 = new Dish(DISH3_ID, "Блюдо 1", LocalDate.of(2020,8,1), 150);
    public static final Dish DISH4 = new Dish(DISH4_ID, "Супчик 2", LocalDate.of(2020,8,1), 100);
    public static final Dish DISH5 = new Dish(DISH5_ID, "Гарнир 2", LocalDate.of(2020,8,1), 50);
    public static final Dish DISH6 = new Dish(DISH6_ID, "Блюдо 2", LocalDate.of(2020,8,1), 150);


    public static Dish getNew() {
        return new Dish("NEW Dish", LocalDate.of(2020,9,2), 200);
    }

    public static Dish getUpdated() {
        return new Dish(DISH2_ID, "Updated Dish", LocalDate.of(2020,8,1), 200);
    }
}
