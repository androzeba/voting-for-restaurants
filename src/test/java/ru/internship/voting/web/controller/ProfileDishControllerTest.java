package ru.internship.voting.web.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.internship.voting.DishTestData.DISH4;
import static ru.internship.voting.DishTestData.DISH5;
import static ru.internship.voting.DishTestData.DISH6;
import static ru.internship.voting.DishTestData.DISH7;
import static ru.internship.voting.DishTestData.DISH8;
import static ru.internship.voting.DishTestData.DISH9;
import static ru.internship.voting.DishTestData.DISHES;
import static ru.internship.voting.DishTestData.DISH_MATCHER;
import static ru.internship.voting.RestaurantTestData.RESTAURANT1_ID;
import static ru.internship.voting.TestUtil.userHttpBasic;
import static ru.internship.voting.UserTestData.USER1;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.internship.voting.web.AbstractControllerTest;

class ProfileDishControllerTest extends AbstractControllerTest {

  private static final String RESTAURANT_URL = "/restaurants/";
  private static final String DISH_URL = "/dishes/";

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
}