package ru.internship.voting.web.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.internship.voting.CommonTestData.NOT_FOUND;
import static ru.internship.voting.DishTestData.DISH2_ID;
import static ru.internship.voting.DishTestData.DISH3_ID;
import static ru.internship.voting.DishTestData.DISH4;
import static ru.internship.voting.DishTestData.DISH4_ID;
import static ru.internship.voting.DishTestData.DISH_MATCHER;
import static ru.internship.voting.RestaurantTestData.RESTAURANT1_ID;
import static ru.internship.voting.RestaurantTestData.RESTAURANT2_ID;
import static ru.internship.voting.RestaurantTestData.RESTAURANT3_ID;
import static ru.internship.voting.TestUtil.readFromJson;
import static ru.internship.voting.TestUtil.userHttpBasic;
import static ru.internship.voting.UserTestData.ADMIN;
import static ru.internship.voting.util.ValidationUtil.checkNotFoundWithId;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.internship.voting.DishTestData;
import ru.internship.voting.model.Dish;
import ru.internship.voting.repository.DishRepository;
import ru.internship.voting.util.exception.NotFoundException;
import ru.internship.voting.web.AbstractControllerTest;
import ru.internship.voting.web.json.JsonUtil;

class AdminDishControllerTest extends AbstractControllerTest {

  private static final String RESTAURANT_URL = "/admin/restaurants/";
  private static final String DISH_URL = "/dishes/";

  @Autowired
  private DishRepository dishRepository;

  @Test
  void addNewDish() throws Exception {
    Dish newDish = DishTestData.getNew();
    ResultActions action = perform(MockMvcRequestBuilders.post(RESTAURANT_URL + RESTAURANT3_ID + DISH_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(JsonUtil.writeValue(newDish))
        .with(userHttpBasic(ADMIN)))
        .andExpect(status().isCreated());

    Dish created = readFromJson(action, Dish.class);
    newDish.setId(created.getId());
    DISH_MATCHER.assertMatch(created, newDish);
    DISH_MATCHER.assertMatch(dishRepository.get(created.getId(), RESTAURANT3_ID), newDish);
  }

  @Test
  void getDishById() throws Exception {
    perform(MockMvcRequestBuilders.get(RESTAURANT_URL + RESTAURANT1_ID + DISH_URL + DISH4_ID)
        .with(userHttpBasic(ADMIN)))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(DISH_MATCHER.contentJson(DISH4));
  }

  @Test
  void deleteDish() throws Exception {
    perform(MockMvcRequestBuilders.delete(RESTAURANT_URL + RESTAURANT1_ID + DISH_URL + DISH3_ID)
        .with(userHttpBasic(ADMIN)))
        .andExpect(status().isNoContent());
    assertThrows(NotFoundException.class, () -> checkNotFoundWithId(dishRepository.get(DISH3_ID, RESTAURANT1_ID), DISH3_ID));
  }

  @Test
  void deleteDishNotFound() throws Exception {
    perform(MockMvcRequestBuilders.delete(RESTAURANT_URL + RESTAURANT2_ID + DISH_URL + NOT_FOUND)
        .with(userHttpBasic(ADMIN)))
        .andExpect(status().isUnprocessableEntity());
  }

  @Test
  void updateDish() throws Exception {
    Dish updated = DishTestData.getUpdated();
    perform(MockMvcRequestBuilders.put(RESTAURANT_URL + RESTAURANT1_ID + DISH_URL + DISH2_ID).contentType(MediaType.APPLICATION_JSON)
        .content(JsonUtil.writeValue(updated))
        .with(userHttpBasic(ADMIN)))
        .andExpect(status().isNoContent());

    DISH_MATCHER.assertMatch(dishRepository.get(DISH2_ID, RESTAURANT1_ID), updated);

  }
}