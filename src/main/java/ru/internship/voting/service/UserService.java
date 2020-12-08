package ru.internship.voting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.internship.voting.AuthorizedUser;
import ru.internship.voting.model.User;
import ru.internship.voting.repository.UserRepository;

@Service("userService")
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.getByEmail(email.toLowerCase());
    if (user == null) {
      throw new UsernameNotFoundException("User " + email + " is not found");
    }
    return new AuthorizedUser(user);
  }

}
