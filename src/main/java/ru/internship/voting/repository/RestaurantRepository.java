package ru.internship.voting.repository;

import org.springframework.data.domain.Sort;
import ru.internship.voting.model.Restaurant;

import java.util.List;

public class RestaurantRepository {

    private final DataJpaRestaurantRepository jpaRestaurantRepository;
    private static final Sort SORT_NAME = Sort.by(Sort.Direction.ASC, "name");

    public RestaurantRepository(DataJpaRestaurantRepository jpaRestaurantRepository) {
        this.jpaRestaurantRepository = jpaRestaurantRepository;
    }

    public List<Restaurant> getAll() {
        return jpaRestaurantRepository.findAll(SORT_NAME);
    }

    public Restaurant get(int id) {
        return jpaRestaurantRepository.findById(id).orElse(null);
    }

    public boolean delete(int id) {
        return jpaRestaurantRepository.delete(id) != 0;
    }

    public Restaurant save(Restaurant restaurant) {
        return jpaRestaurantRepository.save(restaurant);
    }

    public Restaurant getWithDishes(int id) {
        return jpaRestaurantRepository.getWithDishes(id);
    }
}