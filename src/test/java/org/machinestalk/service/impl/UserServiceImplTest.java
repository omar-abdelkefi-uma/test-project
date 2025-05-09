package org.machinestalk.service.impl;

import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.machinestalk.api.dto.AddressDto;
import org.machinestalk.api.dto.UserRegistrationDto;
import org.machinestalk.domain.Address;
import org.machinestalk.domain.Department;
import org.machinestalk.domain.User;
import org.machinestalk.repository.DepartmentRepository;
import org.machinestalk.repository.UserRepository;
import org.machinestalk.service.impl.UserServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import reactor.test.StepVerifier;

class UserServiceImplTest {

	@Mock
	private UserRepository userRepository;
	@Mock
	private DepartmentRepository departmentRepository;
	@InjectMocks
	private UserServiceImpl userService; // Use the concrete implementation

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void shouldRetrieveUserById_whenGetById() {
		// Given
		Department department = new Department();
		department.setName("RH");

		Address address = new Address();
		address.setStreetNumber("20");
		address.setStreetName("Rue Jean Jacques Rousseau");
		address.setPostalCode("75002");
		address.setCity("Paris");
		address.setCountry("France");

		User user = new User();
		user.setId(12345L);
		user.setFirstName("Dupont");
		user.setLastName("Emilie");
		user.setDepartment(department);
		user.setAddresses(singleton(address));

		when(userRepository.findById(12345L)).thenReturn(Optional.of(user));

		// When + Then
		StepVerifier.create(userService.getById(12345L)).assertNext(result -> {
			assertNotNull(result);
			assertEquals("Dupont", result.getFirstName());
			assertEquals("Emilie", result.getLastName());
			assertEquals("RH", result.getDepartment().getName());
			assertEquals("Paris", result.getAddresses().iterator().next().getCity());
		}).verifyComplete();

		verify(userRepository, times(1)).findById(12345L);
	}
}
