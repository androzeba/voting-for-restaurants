package ru.internship.voting.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.internship.voting.model.Vote;

import java.time.LocalDate;
import java.util.List;

@Repository
public class VoteRepository {

    private final DataJpaVoteRepository jpaVoteRepository;
    private final DataJpaUserRepository jpaUserRepository;

    @Autowired
    public VoteRepository(DataJpaVoteRepository jpaVoteRepository, DataJpaUserRepository jpaUserRepository) {
        this.jpaVoteRepository = jpaVoteRepository;
        this.jpaUserRepository = jpaUserRepository;
    }

    public Vote get(int id, int userId) {
        return jpaVoteRepository.findById(id)
                .filter(vote -> vote.getUser().getId() == userId)
                .orElse(null);
    }

    public List<Vote> getAll(int userId) {
        return jpaVoteRepository.getAll(userId);
    }

    public Vote save(Vote vote, int userId) {
        if (!vote.isNew() && get(vote.getId(), userId) == null) {
            return null;
        }
        vote.setUser(jpaUserRepository.getOne(userId));
        return jpaVoteRepository.save(vote);
    }

    public Vote getWithUserAndRestaurant(int id, int userId) {
        return jpaVoteRepository.getWithUserAndRestaurant(id, userId);
    }

    public List<Vote> getAllWithRestaurant(int userId) {
        return jpaVoteRepository.getAllWithRestaurant(userId);
    }

    public List<Vote> getBetween(LocalDate startDate, LocalDate endDate, int userId) {
        return jpaVoteRepository.getBetween(startDate, endDate, userId);
    }
}