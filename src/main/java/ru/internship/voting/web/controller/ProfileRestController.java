package ru.internship.voting.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.internship.voting.model.Dish;
import ru.internship.voting.model.Restaurant;
import ru.internship.voting.model.Vote;
import ru.internship.voting.repository.DishRepository;
import ru.internship.voting.repository.RestaurantRepository;
import ru.internship.voting.repository.VoteRepository;
import ru.internship.voting.util.DateTimeUtil;
import ru.internship.voting.util.ValidationUtil;
import ru.internship.voting.web.SecurityUtil;

import java.time.LocalDate;
import java.util.List;

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
        Restaurant restaurant = ValidationUtil.checkNotFound(restaurantRepository.getWithDishes(id), "Restaurant with id=" + id);
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
        return voteRepository.getBetween(DateTimeUtil.dateOrMin(startDate), DateTimeUtil.dateOrMax(endDate), userId);
    }

//    @PostMapping("/votes/{id}")
//    public Vote createVote(Vote vote) {
//        int userId = SecurityUtil.getAuthUserId();
//        ValidationUtil.checkNew(vote);
//    }


}