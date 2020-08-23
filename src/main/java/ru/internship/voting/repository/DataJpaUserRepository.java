package ru.internship.voting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.internship.voting.model.User;

public interface DataJpaUserRepository extends JpaRepository<User, Integer> {
}