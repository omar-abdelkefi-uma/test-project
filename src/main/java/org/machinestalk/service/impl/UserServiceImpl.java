package org.machinestalk.service.impl;
import org.machinestalk.api.dto.UserRegistrationDto;
import org.machinestalk.domain.User;
import org.machinestalk.repository.UserRepository;
import org.machinestalk.service.UserService;
import reactor.core.publisher.Mono;

public class UserServiceImpl implements UserService {

  private UserRepository userRepository;

  @Override
  public User registerUser(final UserRegistrationDto userRegistrationDto) {
    // implement me !!
    return null;
  }

  @Override
  public Mono<User> getById(final long id) {
    // implement me !!
    return null;
  }
}