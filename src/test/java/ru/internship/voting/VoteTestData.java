package ru.internship.voting;

import ru.internship.voting.model.Vote;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.internship.voting.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {
    public static TestMatcher<Vote> VOTE_MATCHER = TestMatcher.usingFieldsWithIgnoringAssertions(Vote.class, "restaurant", "user");

    public static final int VOTE1_ID = START_SEQ + 53;
    public static final int VOTE2_ID = START_SEQ + 54;
    public static final int VOTE3_ID = START_SEQ + 55;
    public static final int VOTE4_ID = START_SEQ + 56;
    public static final int VOTE5_ID = START_SEQ + 57;
    public static final int VOTE6_ID = START_SEQ + 58;
    public static final int VOTE7_ID = START_SEQ + 59;
    public static final int VOTE8_ID = START_SEQ + 60;
    public static final int VOTE9_ID = START_SEQ + 61;

    public static final Vote VOTE1 = new Vote(VOTE1_ID, LocalDate.of(2020, 8, 1));
    public static final Vote VOTE2 = new Vote(VOTE2_ID, LocalDate.of(2020, 8, 2));
    public static final Vote VOTE3 = new Vote(VOTE3_ID, LocalDate.of(2020, 8, 3));
    public static final Vote VOTE4 = new Vote(VOTE4_ID, LocalDate.of(2020, 8, 4));
    public static final Vote VOTE5 = new Vote(VOTE5_ID, LocalDate.of(2020, 8, 5));
    public static final Vote VOTE6 = new Vote(VOTE6_ID, LocalDate.of(2020, 8, 6));
    public static final Vote VOTE7 = new Vote(VOTE7_ID, LocalDate.of(2020, 8, 7));
    public static final Vote VOTE8 = new Vote(VOTE8_ID, LocalDate.of(2020, 8, 8));
    public static final Vote VOTE9 = new Vote(VOTE9_ID, LocalDate.of(2020, 8, 9));

    public static final List<Vote> VOTES = List.of(VOTE9, VOTE8, VOTE7, VOTE6, VOTE5, VOTE4, VOTE3, VOTE2, VOTE1);

    static {
        VOTE1.setRestaurant(RestaurantTestData.RESTAURANT2);
    }

    public static TestMatcher<Vote> VOTE_WITH_RESTAURANT_MATCHER =
            TestMatcher.usingAssertions(Vote.class,
                    (a, e) -> assertThat(a).usingRecursiveComparison()
                            .ignoringFields("restaurant.dishes").ignoringAllOverriddenEquals().isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });

    public static Vote getNew(LocalDate date) {
        return new Vote(date);
    }

    public static Vote getUpdated() {
        return new Vote(VOTE1_ID, LocalDate.of(2020, 8, 1));
    }
}