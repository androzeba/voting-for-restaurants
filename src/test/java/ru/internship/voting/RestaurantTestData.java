package ru.internship.voting;

import ru.internship.voting.model.Restaurant;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.internship.voting.DishTestData.DISHES;
import static ru.internship.voting.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static TestMatcher<Restaurant> RESTAURANT_MATCHER = TestMatcher.usingFieldsWithIgnoringAssertions(Restaurant.class, "dishes");
    public static TestMatcher<Restaurant> RESTAURANT_WITH_DISHES_MATCHER =
            TestMatcher.usingAssertions(Restaurant.class,
                    (a, e) -> assertThat(a).usingRecursiveComparison()
                            .ignoringFields("dishes.restaurant").ignoringAllOverriddenEquals().isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });

    public static final int RESTAURANT1_ID = START_SEQ + 3;
    public static final int RESTAURANT2_ID = START_SEQ + 4;
    public static final int RESTAURANT3_ID = START_SEQ + 5;
    public static final int RESTAURANT4_ID = START_SEQ + 6;
    public static final int RESTAURANT5_ID = START_SEQ + 7;

    public static final Restaurant RESTAURANT1 = new Restaurant(RESTAURANT1_ID, "Restaurant 1");
    public static final Restaurant RESTAURANT2 = new Restaurant(RESTAURANT2_ID, "Restaurant 2");
    public static final Restaurant RESTAURANT3 = new Restaurant(RESTAURANT3_ID, "Restaurant 3");
    public static final Restaurant RESTAURANT4 = new Restaurant(RESTAURANT4_ID, "Restaurant 4");
    public static final Restaurant RESTAURANT5 = new Restaurant(RESTAURANT5_ID, "Restaurant 5");

    static {
        RESTAURANT1.setDishes(DISHES);
    }

    public static Restaurant getNew() {
        return new Restaurant("NEW Restaurant");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT3_ID, "Updated restaurant");
    }
}