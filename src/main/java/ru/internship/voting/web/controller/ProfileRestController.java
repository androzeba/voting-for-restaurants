package ru.internship.voting.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.internship.voting.model.Dish;
import ru.internship.voting.model.Restaurant;
import ru.internship.voting.model.Vote;
import ru.internship.voting.repository.DishRepository;
import ru.internship.voting.repository.RestaurantRepository;
import ru.internship.voting.repository.VoteRepository;
import ru.internship.voting.util.ValidationUtil;
import ru.internship.voting.web.SecurityUtil;

import java.util.List;

@RestController
public class ProfileRestController {

    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;
    private VoteRepository voteRepository;

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

    @GetMapping("/votes")
    public List<Vote> getAllVotes() {
        int userId = SecurityUtil.getAuthUserId();
        return voteRepository.getAll(userId);
    }

//    @PostMapping("/votes/{id}")
//    public Vote createVote(Vote vote) {
//        int userId = SecurityUtil.getAuthUserId();
//        ValidationUtil.checkNew(vote);
//    }



}
