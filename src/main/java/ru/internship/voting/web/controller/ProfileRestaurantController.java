package ru.internship.voting.web.controller;

import static ru.internship.voting.util.ValidationUtil.checkNotFoundWithId;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.internship.voting.model.Restaurant;
import ru.internship.voting.repository.RestaurantRepository;
import ru.internship.voting.util.DateTimeUtil;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestaurantController {

  private static final Logger log = LoggerFactory.getLogger(ProfileRestaurantController.class);

  private final RestaurantRepository restaurantRepository;

  @Autowired
  public ProfileRestaurantController(RestaurantRepository restaurantRepository) {
    this.restaurantRepository = restaurantRepository;
  }

  @GetMapping("/restaurants")
  public List<Restaurant> getAllRestaurants() {
    log.info("Get all restaurants");
    return restaurantRepository.getAll();
  }

  @GetMapping("/restaurants/with-dishes")
  public List<Restaurant> getAllRestaurantsWithDishes() {
    log.info("Get all restaurants with today's menu");
    return restaurantRepository.getAllWithDishes(DateTimeUtil.getNow());
  }

  @GetMapping("/restaurants/{restId}/with-dishes")
  public Restaurant getRestaurantWithDishes(@PathVariable int restId) {
    log.info("Get restaurant {} with dishes", restId);
    return checkNotFoundWithId(restaurantRepository.getWithDishes(restId), restId);
  }
}
