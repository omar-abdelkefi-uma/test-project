package io.glide.boot.service.impl;

import io.glide.boot.domain.User;
import io.glide.boot.exception.ResourceNotFoundException;
import io.glide.boot.exception.ResourceNotSavedException;
import io.glide.boot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  @Autowired private UserRepository userRepository;

  @Override
  public Mono<User> registerUser(final User user) throws ResourceNotSavedException{
    Optional<User> userOptional = Optional.of(userRepository.save(user));
    return userOptional.map(Mono::just).orElseThrow(() -> new ResourceNotSavedException(User.class, user));
  }

  @Override
  public Mono<User> getById(final long id) throws ResourceNotFoundException {
    Optional<User> userOptional = userRepository.findById(id);
    return userOptional.map(Mono::just).orElseThrow(() ->
            new ResourceNotFoundException(User.class, new HashMap<>(Collections.singletonMap("id", ""+id))));
  }

}
