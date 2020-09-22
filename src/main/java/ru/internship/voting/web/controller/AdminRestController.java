package ru.internship.voting.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.internship.voting.View;
import ru.internship.voting.model.Dish;
import ru.internship.voting.model.Restaurant;
import ru.internship.voting.repository.DishRepository;
import ru.internship.voting.repository.RestaurantRepository;

import java.net.URI;

import static ru.internship.voting.util.ValidationUtil.*;

@RestController
@RequestMapping(value = "/admin/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestController {

    private static final Logger log = LoggerFactory.getLogger(AdminRestController.class);

    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;

    public AdminRestController(RestaurantRepository restaurantRepository, DishRepository dishRepository) {
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
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

    @PostMapping(value = "/{restId}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> addNewDish(@Validated(View.Web.class) @RequestBody Dish dish, @PathVariable int restId) {
        checkNew(dish);
        Assert.notNull(dish, "dish must not be null");
        Dish created = dishRepository.save(dish, restId);
        log.info("Create new dish in restaurant {} by admin", restId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/admin/restaurants/{restId}/dishes")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/{restId}/dishes/{id}")
    public Dish getDishById(@PathVariable int restId, @PathVariable int id) {
        log.info("Get dish {} from restaurant {} by admin", id, restId);
        return checkNotFoundWithId(dishRepository.get(id, restId), id);
    }

    @DeleteMapping("/{restId}/dishes/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteDish(@PathVariable int restId, @PathVariable int id) {
        log.info("Delete dish {} from restaurant {} by admin", id, restId);
        checkNotFoundWithId(dishRepository.delete(id, restId), id);
    }

    @PutMapping(value = "/{restId}/dishes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateDish(@Validated(View.Web.class) @RequestBody Dish dish, @PathVariable int restId, @PathVariable int id) {
        assureIdConsistent(dish, id);
        Assert.notNull(dish, "dish must not be null");
        log.info("Update dish {} from restaurant {} by admin", id, restId);
        checkNotFoundWithId(dishRepository.save(dish, restId), dish.getId());
    }
}