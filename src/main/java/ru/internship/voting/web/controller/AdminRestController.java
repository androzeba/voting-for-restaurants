package ru.internship.voting.web.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.internship.voting.model.Dish;
import ru.internship.voting.model.Restaurant;
import ru.internship.voting.repository.DishRepository;
import ru.internship.voting.repository.RestaurantRepository;
import ru.internship.voting.util.ValidationUtil;

import java.net.URI;

@RestController
@RequestMapping(value = "/admin/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestController {

    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;

    public AdminRestController(RestaurantRepository restaurantRepository, DishRepository dishRepository) {
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> addNewRestaurant(@RequestBody Restaurant restaurant) {
        ValidationUtil.checkNew(restaurant);
        Assert.notNull(restaurant, "restaurant must not be null");
        Restaurant created = restaurantRepository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/admin/restaurants")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/{id}")
    public Restaurant getRestaurantById(@PathVariable int id) {
        return ValidationUtil.checkNotFoundWithId(restaurantRepository.get(id), id);
    }

    @DeleteMapping("/{id}")
    public void deleteRestaurant(@PathVariable int id) {
        ValidationUtil.checkNotFoundWithId(restaurantRepository.delete(id), id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateRestaurant(@RequestBody Restaurant restaurant, @PathVariable int id) {
        ValidationUtil.assureIdConsistent(restaurant, id);
        Assert.notNull(restaurant, "restaurant must not be null");
        restaurantRepository.save(restaurant);
    }

    @PostMapping(value = "/{restId}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> addNewDish(@RequestBody Dish dish, @PathVariable int restId) {
        ValidationUtil.checkNew(dish);
        Assert.notNull(dish, "dish must not be null");
        Dish created = dishRepository.save(dish, restId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/admin/restaurants/{restId}/dishes")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/{restId}/dishes/{id}")
    public Dish getDishById(@PathVariable int restId, @PathVariable int id) {
        return ValidationUtil.checkNotFoundWithId(dishRepository.get(id, restId), id);
    }

    @DeleteMapping("/{restId}/dishes/{id}")
    public void deleteDish(@PathVariable int restId, @PathVariable int id) {
        ValidationUtil.checkNotFoundWithId(dishRepository.delete(id, restId), id);
    }

    @PutMapping(value = "/{restId}/dishes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateDish(@RequestBody Dish dish, @PathVariable int restId, @PathVariable int id) {
        ValidationUtil.assureIdConsistent(dish, id);
        Assert.notNull(dish, "dish must not be null");
        dishRepository.save(dish, restId);
    }
}
