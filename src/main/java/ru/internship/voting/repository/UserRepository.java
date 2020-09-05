package ru.internship.voting.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import ru.internship.voting.AuthorizedUser;
import ru.internship.voting.model.User;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserRepository implements UserDetailsService {

    private final DataJpaUserRepository jpaUserRepository;

    @Autowired
    public UserRepository(DataJpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = jpaUserRepository.getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }
}