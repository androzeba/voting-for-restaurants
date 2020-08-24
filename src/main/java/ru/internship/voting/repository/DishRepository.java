package ru.internship.voting.repository;

import org.springframework.stereotype.Repository;
import ru.internship.voting.model.Dish;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DishRepository {

    private final DataJpaDishRepository jpaDishRepository;

    public DishRepository(DataJpaDishRepository jpaDishRepository) {
        this.jpaDishRepository = jpaDishRepository;
    }

    public Dish get(int id) {
        return jpaDishRepository.findById(id).orElse(null);
    }

    public List<Dish> getAll(int restaurantId) {
        return jpaDishRepository.getAll(restaurantId);
    }

    public boolean delete(int id) {
        return jpaDishRepository.delete(id) != 0;
    }

    public Dish save(Dish dish) {
        return jpaDishRepository.save(dish);
    }

    public List<Dish> getBetween(LocalDate startDate, LocalDate endDate, int restaurantId) {
        return jpaDishRepository.getBetween(startDate, endDate, restaurantId);
    }

    public Dish getWithRestaurant(int id) {
        return jpaDishRepository.getWithRestaurant(id);
    }

}