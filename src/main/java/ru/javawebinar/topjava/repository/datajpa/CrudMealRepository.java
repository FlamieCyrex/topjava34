package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:mealId AND m.user.id=:userId")
    int delete(@Param("mealId") int mealId, @Param("userId") int userId);

    @Transactional
    @Query("SELECT m FROM Meal m WHERE  m.id=:mealId AND m.user.id=:userId")
    Optional<Meal> findById(@Param("mealId") int mealId, @Param("userId") int userId);

    @Transactional
    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId")
    List<Meal> findAll(@Param("userId") int userId, Sort sort);

    @Transactional
    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId AND m.dateTime >= :start AND m.dateTime < :end")
    List<Meal> findAllBetween(@Param("userId") int userId, @Param("start") LocalDateTime start,
                              @Param("end") LocalDateTime end, Sort sort);
}
