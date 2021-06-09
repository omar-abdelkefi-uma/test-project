package io.glide.boot.api.impl;

import io.glide.boot.api.UserApi;
import io.glide.boot.api.dto.UserDto;
import io.glide.boot.api.dto.UserRegistrationDto;
import io.glide.boot.api.dto.mapper.UserMapper;
import io.glide.boot.domain.User;
import io.glide.boot.exception.ResourceNotFoundException;
import io.glide.boot.exception.ResourceNotSavedException;
import io.glide.boot.service.impl.UserServiceImpl;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UserApiImpl implements UserApi {

    private final UserServiceImpl userService;

    public UserApiImpl(final UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public Mono<UserDto> register(UserRegistrationDto userRegistrationDto) throws ResourceNotSavedException {
        /*if (result.hasErrors()) {
            result.getAllErrors().forEach(err -> {
                System.out.println("---------->>>>>>>"+err.toString());
            });
//            ErrorDetails errorDetails =  new ErrorDetails(errors.getAllErrors());
//            System.out.println("---------->>>>>>>"+errorDetails);
            return Mono.error(new ResourceNotSavedException(UserRegistrationDto.class, "invalid agrs params"));
        }*/

        Mono<User> user = userService.registerUser(UserMapper.mapToUser(userRegistrationDto));
        return user.flatMap(userToDto -> Mono.just(UserMapper.mapToUserDto(userToDto)));
//                .onErrorResume(e -> Mono.error(new ResourceNotSavedException(User.class, userRegistrationDto)));
    }

    @Override
    public Mono<UserDto> findUserById(long userId) throws ResourceNotFoundException {
        Mono<User> userMono = userService.getById(userId);
        return userMono.flatMap(user -> Mono.just(UserMapper.mapToUserDto(user)));
//                .onErrorResume(e -> Mono.error(new ResourceNotFoundException("User not found with userId " + userId)));
    }
}
