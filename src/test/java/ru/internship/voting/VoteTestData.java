package ru.internship.voting;

import ru.internship.voting.model.Dish;
import ru.internship.voting.model.Vote;

import java.time.LocalDate;

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

    public static final Vote VOTE1 = new Vote(LocalDate.of(2020,8,1));




    public static Vote getNew() {
        return new Vote(LocalDate.of(2020,9,2));
    }

    public static Vote getUpdated() {
        return new Vote(VOTE1_ID, LocalDate.of(2020,8,1));
    }
}
