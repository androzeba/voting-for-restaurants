package ru.internship.voting.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
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

import static ru.internship.voting.util.ValidationUtil.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestController {

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
        return restaurantRepository.getAll();
    }

    @GetMapping("/restaurants/{id}/dishes")
    public List<Dish> getAllDishes(@PathVariable int id) {
        Restaurant restaurant = checkNotFound(restaurantRepository.getWithDishes(id), "Restaurant with id=" + id);
        return restaurant.getDishes();
    }

    @GetMapping("/restaurants/{id}/dishes/filter")
    public List<Dish> getFilteredDishes(@PathVariable int id,
                                        @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                        @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return dishRepository.getBetween(DateTimeUtil.dateOrMin(startDate), DateTimeUtil.dateOrMax(endDate), id);
    }

    @GetMapping("/votes")
    public List<Vote> getAllVotes() {
        int userId = SecurityUtil.getAuthUserId();
        return voteRepository.getAllWithRestaurant(userId);
    }

    @GetMapping("/votes/filter")
    public List<Vote> getFilteredVotes(@RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                       @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        int userId = SecurityUtil.getAuthUserId();
        return voteRepository.getBetweenWithRestaurant(DateTimeUtil.dateOrMin(startDate), DateTimeUtil.dateOrMax(endDate), userId);
    }

    @PostMapping(value = "/dovote/{restId}")
    public ResponseEntity<Vote> createVote(@PathVariable int restId) {
        int userId = SecurityUtil.getAuthUserId();
        LocalDate date = LocalDate.now();
        Vote currentVote = voteRepository.getByDateAndUserId(date, userId);
        if (currentVote == null) {
            Vote vote = new Vote();
            vote.setDate(date);
            Vote created = voteRepository.save(vote, userId, restId);
            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/votes")
                    .buildAndExpand(created.getId())
                    .toUri();
            return ResponseEntity.created(uriOfNewResource).body(created);
        }
        if (DateTimeUtil.isTimeToUpdate()) {
            Vote updated = checkNotFoundWithId(voteRepository.save(currentVote, userId, restId), currentVote.getId());
            return ResponseEntity.ok(updated);
        }
        throw new IllegalRequestDataException("no more voting available today");
    }




}