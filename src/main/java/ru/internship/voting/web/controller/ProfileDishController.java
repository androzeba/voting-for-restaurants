package ru.internship.voting.web.controller;

import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.internship.voting.model.Dish;
import ru.internship.voting.repository.DishRepository;
import ru.internship.voting.util.DateTimeUtil;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileDishController {

  private static final Logger log = LoggerFactory.getLogger(ProfileDishController.class);

  private final DishRepository dishRepository;

  @Autowired
  public ProfileDishController(DishRepository dishRepository) {
    this.dishRepository = dishRepository;
  }

  @GetMapping("/restaurants/{restId}/dishes")
  public List<Dish> getAllDishesByRestId(@PathVariable int restId) {
    log.info("Get all dishes from restaurant {}", restId);
    return dishRepository.getAll(restId);
  }

  @GetMapping("/restaurants/{restId}/dishes/filter")
  public List<Dish> getFilteredDishesByRestId(@PathVariable int restId,
      @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
      @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
    log.info("Get dishes from restaurant {} between dates ({} - {})", restId, startDate, endDate);
    return dishRepository.getBetween(DateTimeUtil.dateOrMin(startDate), DateTimeUtil.dateOrMax(endDate), restId);
  }
}
