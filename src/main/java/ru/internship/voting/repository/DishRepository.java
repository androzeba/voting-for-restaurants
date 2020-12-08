package ru.internship.voting.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.internship.voting.model.Dish;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DishRepository {

    private final DataJpaDishRepository jpaDishRepository;
    private final DataJpaRestaurantRepository jpaRestaurantRepository;

    @Autowired
    public DishRepository(DataJpaDishRepository jpaDishRepository, DataJpaRestaurantRepository jpaRestaurantRepository) {
        this.jpaDishRepository = jpaDishRepository;
        this.jpaRestaurantRepository = jpaRestaurantRepository;
    }

    public Dish get(int id, int restId) {
        return jpaDishRepository.findById(id)
                .filter(dish -> dish.getRestaurant().getId() == restId)
                .orElse(null);
    }

    public List<Dish> getAll(int restaurantId) {
        return jpaDishRepository.getAll(restaurantId);
    }

    public boolean delete(int id, int restId) {
        return jpaDishRepository.delete(id, restId) != 0;
    }

    @Transactional
    public Dish save(Dish dish, int restId) {
        if (!dish.isNew() && get(dish.getId(), restId) == null) {
            return null;
        }
        dish.setRestaurant(jpaRestaurantRepository.getOne(restId));
        return jpaDishRepository.save(dish);
    }

    public List<Dish> getBetween(LocalDate startDate, LocalDate endDate, int restaurantId) {
        return jpaDishRepository.getBetween(startDate, endDate, restaurantId);
    }
}