package io.glide.boot.api.impl;

import io.glide.boot.api.UserApi;
import io.glide.boot.api.dto.UserDto;
import io.glide.boot.api.dto.UserRegistrationDto;
import io.glide.boot.api.dto.mapper.UserMapper;
import io.glide.boot.domain.User;
import io.glide.boot.exception.ResourceNotFoundException;
import io.glide.boot.exception.ResourceNotSavedException;
import io.glide.boot.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UserApiImpl implements UserApi {

    private static final Logger logger = LoggerFactory.getLogger(UserApiImpl.class);

    private final UserServiceImpl userService;

    public UserApiImpl(final UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public Mono<UserDto> register(UserRegistrationDto userRegistrationDto) throws ResourceNotSavedException {
        Mono<User> user = userService.registerUser(UserMapper.mapToUser(userRegistrationDto));
        return user.flatMap(userToDto -> Mono.just(UserMapper.mapToUserDto(userToDto)));
    }

    @Override
    public Mono<UserDto> findUserById(long userId) throws ResourceNotFoundException {
        Mono<User> userMono = userService.getById(userId);
        return userMono.flatMap(user -> Mono.just(UserMapper.mapToUserDto(user)));
    }
}
