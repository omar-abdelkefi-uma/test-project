package io.glide.service.impl;

import io.glide.boot.api.dto.UserRegistrationDto;
import io.glide.boot.domain.Address;
import io.glide.boot.domain.User;
import io.glide.boot.repository.UserRepository;
import io.glide.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User registerUser(final UserRegistrationDto userRegistrationDto) {
    ModelMapper modelMapper = new ModelMapper();

    Address principalAddress = modelMapper.map(userRegistrationDto.getPrincipalAddress(), Address.class);
    Address secondaryAddress = modelMapper.map(userRegistrationDto.getSecondaryAddress(), Address.class);

    modelMapper.addMappings(new PropertyMap<UserRegistrationDto, User>() {
      @Override
      protected void configure() {
        map().getAddresses().add(principalAddress);
        map().getAddresses().add(secondaryAddress);
      }
    });

    User user = modelMapper.map(userRegistrationDto, User.class);

    return userRepository.save(user);
  }

  @Override
  public Mono<User> getById(final long id) {
    Mono<User> userMono = Optional.of(userRepository.findById(id));
    return userMono;
  }
}
