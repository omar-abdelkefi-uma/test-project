package io.glide.boot.api.impl;

import io.glide.boot.api.UserApi;
import io.glide.boot.api.dto.UserDto;
import io.glide.boot.api.dto.UserRegistrationDto;
import io.glide.boot.domain.User;
import io.glide.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UserApiImpl implements UserApi {

  private final UserService userService;

  public UserApiImpl(UserService userService) {
    this.userService = userService;
  }

  @Override
  public UserDto register(final UserRegistrationDto userRegistrationDto) {
    ModelMapper modelMapper = new ModelMapper();
    return modelMapper.map(userService.registerUser(userRegistrationDto), UserDto.class);
  }

  @Override
  public Mono<UserDto> findUserById(long id) {
    Mono<User> userMono = userService.getById(id);
    Mono<UserDto> userDtoMono = null;
    return userDtoMono;
  }
}
