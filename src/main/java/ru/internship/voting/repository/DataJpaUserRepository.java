package ru.internship.voting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.internship.voting.model.User;

@Transactional(readOnly = true)
public interface DataJpaUserRepository extends JpaRepository<User, Integer> {
}