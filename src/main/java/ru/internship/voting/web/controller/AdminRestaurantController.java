package ru.internship.voting.web.controller;

import static ru.internship.voting.util.ValidationUtil.assureIdConsistent;
import static ru.internship.voting.util.ValidationUtil.checkNew;
import static ru.internship.voting.util.ValidationUtil.checkNotFoundWithId;

import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.internship.voting.View;
import ru.internship.voting.model.Restaurant;
import ru.internship.voting.repository.RestaurantRepository;

@RestController
@RequestMapping(value = "/admin/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController {

  private static final Logger log = LoggerFactory.getLogger(AdminRestaurantController.class);

  private final RestaurantRepository restaurantRepository;

  @Autowired
  public AdminRestaurantController(RestaurantRepository restaurantRepository) {
    this.restaurantRepository = restaurantRepository;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Restaurant> addNewRestaurant(@Validated(View.Web.class) @RequestBody Restaurant restaurant) {
    checkNew(restaurant);
    Assert.notNull(restaurant, "restaurant must not be null");
    log.info("Create new restaurant by admin");
    Restaurant created = restaurantRepository.save(restaurant);
    URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
        .path("/admin/restaurants")
        .buildAndExpand(created.getId())
        .toUri();
    return ResponseEntity.created(uriOfNewResource).body(created);
  }

  @GetMapping("/{restId}")
  public Restaurant getRestaurantById(@PathVariable int restId) {
    log.info("Get restaurant {} by admin", restId);
    return checkNotFoundWithId(restaurantRepository.get(restId), restId);
  }

  @DeleteMapping("/{restId}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void deleteRestaurant(@PathVariable int restId) {
    log.info("Delete restaurant {} by admin", restId);
    checkNotFoundWithId(restaurantRepository.delete(restId), restId);
  }

  @PutMapping(value = "/{restId}", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void updateRestaurant(@Validated(View.Web.class) @RequestBody Restaurant restaurant, @PathVariable int restId) {
    assureIdConsistent(restaurant, restId);
    Assert.notNull(restaurant, "restaurant must not be null");
    checkNotFoundWithId(restaurantRepository.get(restId) != null, restId);
    log.info("Update restaurant {} by admin", restId);
    restaurantRepository.save(restaurant);
  }

}
