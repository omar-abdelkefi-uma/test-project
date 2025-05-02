package org.machinestalk.api.impl;

import org.machinestalk.api.UserApi;
import org.machinestalk.api.dto.UserDto;
import org.machinestalk.api.dto.UserRegistrationDto;
import org.machinestalk.service.UserService;
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
