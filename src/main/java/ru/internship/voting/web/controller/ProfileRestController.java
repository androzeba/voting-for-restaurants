package ru.internship.voting.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.internship.voting.model.Dish;
import ru.internship.voting.model.Restaurant;
import ru.internship.voting.model.Vote;
import ru.internship.voting.repository.DishRepository;
import ru.internship.voting.repository.RestaurantRepository;
import ru.internship.voting.repository.VoteRepository;
import ru.internship.voting.util.DateTimeUtil;
import ru.internship.voting.util.exception.IllegalRequestDataException;
import ru.internship.voting.web.SecurityUtil;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.internship.voting.util.ValidationUtil.checkNotFoundWithId;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestController {

    private static final Logger log = LoggerFactory.getLogger(ProfileRestController.class);

    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;
    private final VoteRepository voteRepository;

    @Autowired
    public ProfileRestController(RestaurantRepository restaurantRepository, DishRepository dishRepository, VoteRepository voteRepository) {
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
        this.voteRepository = voteRepository;
    }

    @GetMapping("/restaurants")
    public List<Restaurant> getAllRestaurants() {
        log.info("Get all restaurants");
        return restaurantRepository.getAll();
    }

    @GetMapping("/restaurants/with-dishes")
    public List<Restaurant> getAllRestaurantsWithDishes() {
        log.info("Get all restaurants with dishes");
        return restaurantRepository.getAllWithDishes();
    }

    @GetMapping("/restaurants/{restId}/with-dishes")
    public Restaurant getRestaurantWithDishes(@PathVariable int restId) {
        log.info("Get restaurant {} with dishes", restId);
        return checkNotFoundWithId(restaurantRepository.getWithDishes(restId), restId);
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

    @GetMapping("/votes")
    public List<Vote> getAllVotes() {
        int userId = SecurityUtil.getAuthUserId();
        log.info("Get all votes for user {}", userId);
        return voteRepository.getAll(userId);
    }

    @GetMapping("/votes/filter")
    public List<Vote> getFilteredVotes(@RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                       @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        int userId = SecurityUtil.getAuthUserId();
        log.info("Get all votes for user {} between dates ({} - {})", userId, startDate, endDate);
        return voteRepository.getBetween(DateTimeUtil.dateOrMin(startDate), DateTimeUtil.dateOrMax(endDate), userId);
    }

    @PostMapping(value = "/do-vote/{restId}")
    public ResponseEntity<Vote> createOrUpdateVote(@PathVariable int restId) {
        int userId = SecurityUtil.getAuthUserId();
        LocalDate date = LocalDate.now();
        Vote currentVote = voteRepository.getByDateAndUserId(date, userId);
        if (currentVote == null) {
            Vote vote = new Vote(date);
            log.info("Create new vote for user {}", userId);
            Vote created = voteRepository.save(vote, userId, restId);
            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/votes")
                    .buildAndExpand(created.getId())
                    .toUri();
            return ResponseEntity.created(uriOfNewResource).body(created);
        }
        if (DateTimeUtil.isTimeToUpdate()) {
            log.info("Update vote for user {}", userId);
            Vote updated = checkNotFoundWithId(voteRepository.save(currentVote, userId, restId), currentVote.getId());
            return ResponseEntity.ok(updated);
        }
        throw new IllegalRequestDataException("no more voting available today");
    }
}