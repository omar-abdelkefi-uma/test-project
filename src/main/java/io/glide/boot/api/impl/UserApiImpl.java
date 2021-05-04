package io.glide.boot.api.impl;

import io.glide.boot.api.UserApi;
import io.glide.boot.api.dto.UserDto;
import io.glide.boot.api.dto.UserRegistrationDto;
import io.glide.service.UserService;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UserApiImpl implements UserApi {

  private final UserService userService;

  public UserApiImpl(final UserService userService) {
    this.userService = userService;
  }

  @Override
  public UserDto register(final UserRegistrationDto userRegistrationDto) {
    // implement me !!
    return null;
  }

  @Override
  public Mono<UserDto> findUserById(long id) {
    // implement me !!
    return null;
  }
}
