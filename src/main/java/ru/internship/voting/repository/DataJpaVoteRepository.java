package ru.internship.voting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.internship.voting.model.Vote;

import java.time.LocalDate;
import java.util.List;

@Component
@Transactional(readOnly = true)
public interface DataJpaVoteRepository extends JpaRepository<Vote, Integer> {

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId ORDER BY v.date DESC")
    List<Vote> getAll(@Param("userId") int userId);

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.user.id=:userId ORDER BY v.date DESC")
    List<Vote> getAllWithRestaurant(@Param("userId") int userId);

//    @Modifying
    @Query("SELECT v FROM Vote v WHERE v.date=:date AND v.user.id=:userId")
    Vote getByDateAndUserId(@Param("date") LocalDate date, @Param("userId") int userId);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.date>=:startDate AND v.date<=:endDate ORDER BY v.date DESC")
    List<Vote> getBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("userId") int userId);

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.user.id=:userId AND v.date>=:startDate AND v.date<=:endDate ORDER BY v.date DESC")
    List<Vote> getBetweenWithRestaurant(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("userId") int userId);
}