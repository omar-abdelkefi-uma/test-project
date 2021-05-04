package io.glide.boot.repository;

import io.glide.boot.domain.Address;
import io.glide.boot.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryTest {

  @Autowired private UserRepository userRepository;

  @Test
  void Should_PersistNewUser_When_Save() {
    // Given
    final Address address = new Address();
    address.setStreetNumber("22");
    address.setStreetName("Rue Voltaire");
    address.setPostalCode("75012");
    address.setCity("Paris");
    address.setCountry("France");

    final User user = new User();
    user.setId(12345L);
    user.setFirstName("Jack");
    user.setLastName("Sparrow");
    user.setAddresses(singleton(address));

    // When
    final User result = userRepository.save(user);

    // Then
    assertNotNull(result);
    assertNotNull(result.getId());
    user.setId(result.getId());
    assertEquals(user, result);
  }

  @Test
  void Should_GetUserByItsId_When_FindById() {
    // Given
    final Address address = new Address();
    address.setStreetNumber("22");
    address.setStreetName("Rue Voltaire");
    address.setPostalCode("75012");
    address.setCity("Paris");
    address.setCountry("France");

    final User user = new User();
    user.setId(12345L);
    user.setFirstName("Jack");
    user.setLastName("Sparrow");
    user.setAddresses(singleton(address));

    final User savedUser = userRepository.save(user);

    // When
    final Optional<User> result = userRepository.findById(savedUser.getId());

    // Then
    assertNotNull(result);
    assertTrue(result.isPresent());
    result.ifPresent(usr -> assertEquals(usr, user));
  }

  @Test
  void Should_returnEmptyOptional_When_FindById_ForUserThatNotExistsByTheGivenId() {
    // When
    final Optional<User> result = userRepository.findById(5678L);

    // Then
    assertNotNull(result);
    assertFalse(result.isPresent());
  }
}
