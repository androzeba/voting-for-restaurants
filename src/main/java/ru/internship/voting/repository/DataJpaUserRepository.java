package ru.internship.voting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.internship.voting.model.User;

@Component
@Transactional(readOnly = true)
public interface DataJpaUserRepository extends JpaRepository<User, Integer> {

    User getByEmail(String email);
}