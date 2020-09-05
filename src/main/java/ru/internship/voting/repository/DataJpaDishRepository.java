package ru.internship.voting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.internship.voting.model.Dish;

import java.time.LocalDate;
import java.util.List;

@Component
@Transactional(readOnly = true)
public interface DataJpaDishRepository extends JpaRepository<Dish, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Dish d WHERE d.id=:id AND d.restaurant.id=:restId")
    int delete(@Param("id") int id, @Param("restId") int restId);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId ORDER BY d.date DESC")
    List<Dish> getAll(@Param("restaurantId") int restaurantId);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId AND d.date>=:startDate AND d.date<=:endDate ORDER BY d.date DESC")
    List<Dish> getBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("restaurantId") int restaurantId);

//    @Query("SELECT d FROM Dish d JOIN FETCH d.restaurant WHERE d.id=:id")
//    Dish getWithRestaurant(@Param("id") int id);
}