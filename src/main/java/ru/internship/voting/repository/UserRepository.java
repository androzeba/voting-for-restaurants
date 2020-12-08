package ru.internship.voting.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.internship.voting.model.User;

@Repository
public class UserRepository {

    private final DataJpaUserRepository jpaUserRepository;

    @Autowired
    public UserRepository(DataJpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    public User getByEmail(String email) {
        return jpaUserRepository.getByEmail(email);
    }
}