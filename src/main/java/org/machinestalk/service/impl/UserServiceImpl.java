package org.machinestalk.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.machinestalk.api.dto.AddressDto;
import org.machinestalk.api.dto.UserRegistrationDto;
import org.machinestalk.domain.Address;
import org.machinestalk.domain.Department;
import org.machinestalk.domain.User;
import org.machinestalk.mapper.AddressMapper;
import org.machinestalk.repository.DepartmentRepository;
import org.machinestalk.repository.UserRepository;
import org.machinestalk.service.UserService;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final DepartmentRepository departmentRepository;

	public UserServiceImpl(UserRepository userRepository, DepartmentRepository departmentRepository) {
		this.userRepository = userRepository;
		this.departmentRepository = departmentRepository;
	}

	@Override
	public User registerUser(final UserRegistrationDto dto) {
		// 1. Check if department exists or create new
		Department department = departmentRepository.findByName(dto.getDepartment()).orElseGet(() -> {
			Department newDept = new Department();
			newDept.setName(dto.getDepartment());
			return departmentRepository.save(newDept);
		});

		Set<Address> addressSet = new HashSet<>();
		addressSet.add(AddressMapper.mapToAddress(dto.getPrincipalAddress()));
		if (dto.getSecondaryAddress() != null) {
			addressSet.add(AddressMapper.mapToAddress(dto.getSecondaryAddress()));
		}
		User user = new User();
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setDepartment(department);
		user.setAddresses(addressSet);
		return userRepository.save(user);
	}

	@Override
	public Mono<User> getById(final long id) {
		return Mono.justOrEmpty(userRepository.findById(id));
	}

}