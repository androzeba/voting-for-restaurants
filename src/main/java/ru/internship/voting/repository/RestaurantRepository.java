package ru.internship.voting.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.internship.voting.model.Restaurant;

import java.util.List;

@Repository
public class RestaurantRepository {

    private static final Sort SORT_NAME = Sort.by(Sort.Direction.ASC, "name");

    private final DataJpaRestaurantRepository jpaRestaurantRepository;

    @Autowired
    public RestaurantRepository(DataJpaRestaurantRepository jpaRestaurantRepository) {
        this.jpaRestaurantRepository = jpaRestaurantRepository;
    }

    public Restaurant get(int id) {
        return jpaRestaurantRepository.findById(id).orElse(null);
    }

    public Restaurant getWithDishes(int id) {
        return jpaRestaurantRepository.getWithDishes(id);
    }

    public List<Restaurant> getAll() {
        return jpaRestaurantRepository.findAll(SORT_NAME);
    }

    public List<Restaurant> getAllWithDishes() {
        return jpaRestaurantRepository.getAllWithDishes();
    }

    public boolean delete(int id) {
        return jpaRestaurantRepository.delete(id) != 0;
    }

    public Restaurant save(Restaurant restaurant) {
        return jpaRestaurantRepository.save(restaurant);
    }
}