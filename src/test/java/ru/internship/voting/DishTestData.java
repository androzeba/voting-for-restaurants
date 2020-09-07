package ru.internship.voting;

import ru.internship.voting.model.Dish;

import java.time.LocalDate;
import java.util.List;

public class DishTestData {
    public static TestMatcher<Dish> DISH_MATCHER = TestMatcher.usingFieldsWithIgnoringAssertions(Dish.class, "restaurant");


    public static final int DISH1_ID = 100008;
    public static final int DISH2_ID = 100009;
    public static final int DISH3_ID = 100010;
    public static final int DISH4_ID = 100023;
    public static final int DISH5_ID = 100025;
    public static final int DISH6_ID = 100024;
    public static final int DISH7_ID = 100038;
    public static final int DISH8_ID = 100039;
    public static final int DISH9_ID = 100040;

    public static final Dish DISH1 = new Dish(DISH1_ID, "Салат 1", LocalDate.of(2020, 8, 1), 50);
    public static final Dish DISH2 = new Dish(DISH2_ID, "Гарнир 1", LocalDate.of(2020, 8, 1), 50);
    public static final Dish DISH3 = new Dish(DISH3_ID, "Блюдо 1", LocalDate.of(2020, 8, 1), 150);
    public static final Dish DISH4 = new Dish(DISH4_ID, "Супчик 1", LocalDate.of(2020, 8, 2), 100);
    public static final Dish DISH5 = new Dish(DISH5_ID, "Напиток 1", LocalDate.of(2020, 8, 2), 100);
    public static final Dish DISH6 = new Dish(DISH6_ID, "Блюдо 1", LocalDate.of(2020, 8, 2), 150);
    public static final Dish DISH7 = new Dish(DISH7_ID, "Салат 1", LocalDate.of(2020, 8, 3), 50);
    public static final Dish DISH8 = new Dish(DISH8_ID, "Гарнир 1", LocalDate.of(2020, 8, 3), 50);
    public static final Dish DISH9 = new Dish(DISH9_ID, "Блюдо 1", LocalDate.of(2020, 8, 3), 150);

    public static final List<Dish> DISHES = List.of(DISH9, DISH8, DISH7, DISH6, DISH5, DISH4, DISH3, DISH2, DISH1);

    public static Dish getNew() {
        return new Dish("NEW Dish", LocalDate.of(2020, 9, 2), 200);
    }

    public static Dish getUpdated() {
        return new Dish(DISH2_ID, "Updated Dish", LocalDate.of(2020, 8, 1), 200);
    }
}