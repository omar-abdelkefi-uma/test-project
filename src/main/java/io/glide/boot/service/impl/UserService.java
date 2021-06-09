package io.glide.boot.service.impl;

import io.glide.boot.domain.User;
import io.glide.boot.exception.ResourceNotFoundException;
import io.glide.boot.exception.ResourceNotSavedException;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface UserService {

  /**
   * Register a new user.
   *
   * @param userRegistrationDto dto for user registration.
   * @return user entity.
   */
  Mono<User> registerUser(@NotNull @Valid User userRegistrationDto) throws ResourceNotSavedException;

  /**
   * Get user by its id.
   *
   * @param id user id.
   * @return user entity.
   */
  Mono<User> getById(long id) throws ResourceNotFoundException;

}
