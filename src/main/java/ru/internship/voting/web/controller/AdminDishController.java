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
import ru.internship.voting.model.Dish;
import ru.internship.voting.repository.DishRepository;

@RestController
@RequestMapping(value = "/admin/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishController {

  private static final Logger log = LoggerFactory.getLogger(AdminDishController.class);

  private final DishRepository dishRepository;

  @Autowired
  public AdminDishController(DishRepository dishRepository) {
    this.dishRepository = dishRepository;
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
