package org.machinestalk.api.impl;

import org.machinestalk.api.UserApi;
import org.machinestalk.api.dto.UserDto;
import org.machinestalk.api.dto.UserRegistrationDto;
import org.machinestalk.domain.User;
import org.machinestalk.mapper.UserMapper;
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
		User user = userService.registerUser(userRegistrationDto);
		return UserMapper.mapToUsersDto(user, new UserDto());
	}

	@Override
	public Mono<UserDto> findUserById(long id) {
		return userService.getById(id).map(user -> UserMapper.mapToUsersDto(user, new UserDto()));
	}

}