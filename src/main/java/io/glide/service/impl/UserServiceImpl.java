package io.glide.service.impl;

import io.glide.boot.domain.User;
import io.glide.boot.api.dto.UserRegistrationDto;
import io.glide.boot.repository.UserRepository;
import io.glide.service.UserService;
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
