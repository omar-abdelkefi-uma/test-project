package io.glide.boot.api;

import io.glide.boot.api.dto.UserDto;
import io.glide.boot.api.dto.UserRegistrationDto;
import io.glide.boot.exception.ResourceNotFoundException;
import io.glide.boot.exception.ResourceNotSavedException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(produces = APPLICATION_JSON_VALUE)
public interface UserApi {

  /**
   * Register a new user.
   *
   * @param userRegistrationDto DTO that input data needed to register a new user.
   * @return registered user infos.
   */
  @PostMapping("/users/register")
  @ResponseStatus(HttpStatus.CREATED)
  Mono<UserDto> register(@RequestBody @Valid UserRegistrationDto userRegistrationDto) throws ResourceNotSavedException;

  /**
   * Get user details by id.
   *
   * @param userId user id
   * @return user infos
   */
  @GetMapping("/users/{userId}")
  @ResponseStatus(HttpStatus.OK)
  Mono<UserDto> findUserById(@PathVariable long userId) throws ResourceNotFoundException;

}
