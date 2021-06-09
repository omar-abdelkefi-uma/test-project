package io.glide.boot.service.impl;

import io.glide.boot.api.dto.AddressDto;
import io.glide.boot.api.dto.UserRegistrationDto;
import io.glide.boot.domain.Address;
import io.glide.boot.domain.Department;
import io.glide.boot.domain.User;
import io.glide.boot.exception.ResourceNotFoundException;
import io.glide.boot.exception.ResourceNotSavedException;
import io.glide.boot.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Optional;

import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class UserServiceImplTest {

  @Mock private UserRepository userRepository;

  @InjectMocks private UserServiceImpl userService;

  @BeforeEach
  void setUp() {
    openMocks(this);
  }

  @Test
  void Should_RegisterNewUser_When_RegisterUser() throws ResourceNotSavedException {
    // Given
    final AddressDto addressDto = new AddressDto();
    addressDto.setStreetNumber("20");
    addressDto.setStreetName("Rue de Voltaire");
    addressDto.setPostalCode("75015");
    addressDto.setCity("Paris");
    addressDto.setCountry("France");

    final UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
    userRegistrationDto.setFirstName("Jack");
    userRegistrationDto.setLastName("Sparrow");
    userRegistrationDto.setDepartment("RH");
    userRegistrationDto.setPrincipalAddress(addressDto);

    final Department department = new Department();
    department.setName(userRegistrationDto.getDepartment());
    final Address address = new Address();
    address.setStreetNumber(userRegistrationDto.getPrincipalAddress().getStreetNumber());
    address.setStreetName(userRegistrationDto.getPrincipalAddress().getStreetName());
    address.setPostalCode(userRegistrationDto.getPrincipalAddress().getPostalCode());
    address.setCity(userRegistrationDto.getPrincipalAddress().getCity());
    address.setCountry(userRegistrationDto.getPrincipalAddress().getCountry());
    final User user = new User();
    user.setId(12345L);
    user.setFirstName(userRegistrationDto.getFirstName());
    user.setLastName(userRegistrationDto.getLastName());
    user.setDepartment(department);
    user.setAddresses(singleton(address));

    when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

    // When
    final Mono<User> userMono = userService.registerUser(user);

    // Then
    verify(userRepository, times(1)).save(any(User.class));

    StepVerifier.create(userMono)
            .assertNext(result -> {
              // Then
              assertNotNull(result);
              assertNotNull(result.getId());
              assertNotNull(result.getAddresses());
              user.setId(result.getId());
              assertEquals(user, result);
            }).verifyComplete();
  }

  @Test
  void Should_RetrieveUserByTheGivenId_When_GetById() {
    // Given
    final Department department = new Department();
    department.setName("RH");
    final Address address = new Address();
    address.setStreetNumber("20");
    address.setStreetName("Rue Jean Jacques Rousseau");
    address.setPostalCode("75002");
    address.setCity("Paris");
    address.setCountry("France");
    final User user = new User();
    user.setId(12345L);
    user.setFirstName("Dupont");
    user.setLastName("Emilie");
    user.setDepartment(department);
    user.setAddresses(singleton(address));

    when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

    // When &&
    try {
      StepVerifier.create(userService.getById(user.getId()))
          .assertNext( result -> {
                // Then
                assertNotNull(result);
                assertEquals(user, result);
          }).verifyComplete();
    } catch (ResourceNotFoundException e) {
      e.printStackTrace();
    }
  }
}
