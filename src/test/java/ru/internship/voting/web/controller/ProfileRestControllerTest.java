package ru.internship.voting.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.internship.voting.repository.RestaurantRepository;
import ru.internship.voting.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.internship.voting.DishTestData.*;
import static ru.internship.voting.RestaurantTestData.*;
import static ru.internship.voting.TestUtil.userHttpBasic;
import static ru.internship.voting.UserTestData.*;
import static ru.internship.voting.VoteTestData.*;

class ProfileRestControllerTest extends AbstractControllerTest {

    private static final String VOTE_URL = "/votes/";
    private static final String RESTAURANT_URL = "/restaurants/";
    private static final String DISH_URL = "/dishes/";

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void getAllRestaurants() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANT_URL)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(RESTAURANT1, RESTAURANT2, RESTAURANT3, RESTAURANT4, RESTAURANT5));
    }

    @Test
    void getRestaurantWithDishes() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANT_URL + RESTAURANT1_ID + "/with-dishes")
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_WITH_DISHES_MATCHER.contentJson(RESTAURANT1));
    }

    @Test
    void getAllDishesByRestId() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANT_URL + RESTAURANT1_ID + DISH_URL)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(DISHES));
    }

    @Test
    void getFilteredDishesByRestId() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANT_URL + RESTAURANT1_ID + DISH_URL + "filter")
                .param("startDate", "2020-08-02")
                .param("endDate", "2020-08-03")
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(DISH9, DISH8, DISH7, DISH6, DISH5, DISH4));
    }

    @Test
    void getAllVotes() throws Exception {
        perform(MockMvcRequestBuilders.get(VOTE_URL)
                .with(userHttpBasic(USER2)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(VOTE7, VOTE5, VOTE2));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(VOTE_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getFilteredVotes() throws Exception {
        perform(MockMvcRequestBuilders.get(VOTE_URL + "filter")
                .param("startDate", "2020-08-04")
                .param("endDate", "2020-08-07")
                .with(userHttpBasic(USER2)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(VOTE7, VOTE5));
    }

    @Test
    void createVote() throws Exception {
//        Vote newVote = new Vote(LocalDate.now());
//        newVote.setRestaurant(RESTAURANT2);
//        ResultActions action = perform(MockMvcRequestBuilders.post("/do-vote/" + RESTAURANT2_ID)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
//        Vote created = readFromJson(action, Vote.class);
//        newVote.setId(created.getId());
//        VOTE_MATCHER.assertMatch(created, newVote);
//        RESTAURANT_MATCHER.assertMatch(created.getRestaurant(), RESTAURANT2);
    }

    @Test
    void updateVote() {
    }
}