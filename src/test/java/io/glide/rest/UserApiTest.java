package io.glide.rest;

import io.glide.boot.Application;
import io.glide.boot.api.dto.AddressDto;
import io.glide.boot.api.dto.UserDto;
import io.glide.boot.api.dto.UserRegistrationDto;
import io.glide.boot.api.dto.mapper.UserMapper;
import io.glide.boot.domain.Address;
import io.glide.boot.domain.Department;
import io.glide.boot.domain.User;
import io.glide.boot.exception.ErrorDetails;
import io.glide.boot.exception.ResourceNotFoundException;
import io.glide.boot.exception.ResourceNotSavedException;
import io.glide.boot.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserApiTest {

    @Autowired
	private WebTestClient webTestClient;

    @MockBean
    private UserServiceImpl userService;

    @LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

    AddressDto addressDto;
    UserDto userDto;
    UserRegistrationDto userRegistrationDto;
    Department department;
    Address address;
    User user;

    @BeforeEach
    void init() {

        openMocks(this);

        addressDto = new AddressDto();
        addressDto.setStreetNumber("20");
        addressDto.setStreetName("Rue de Voltaire");
        addressDto.setPostalCode("75015");
        addressDto.setCity("Paris");
        addressDto.setCountry("France");

        userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setFirstName("Jack");
        userRegistrationDto.setLastName("Sparrow");
        userRegistrationDto.setDepartment("RH");
        userRegistrationDto.setPrincipalAddress(addressDto);

        department = new Department();
        department.setName(userRegistrationDto.getDepartment());
        department.setId(1L);
        address = new Address();
        address.setStreetNumber(userRegistrationDto.getPrincipalAddress().getStreetNumber());
        address.setStreetName(userRegistrationDto.getPrincipalAddress().getStreetName());
        address.setPostalCode(userRegistrationDto.getPrincipalAddress().getPostalCode());
        address.setCity(userRegistrationDto.getPrincipalAddress().getCity());
        address.setCountry(userRegistrationDto.getPrincipalAddress().getCountry());
        user = new User();
        user.setId(1L);
        user.setFirstName(userRegistrationDto.getFirstName());
        user.setLastName(userRegistrationDto.getLastName());
        user.setDepartment(department);
        user.setAddresses(singleton(address));

        userDto = new UserDto();
        userDto.id = "1";
        userDto.userInfos = new UserDto.UserInfos(userRegistrationDto.getFirstName(), userRegistrationDto.getLastName(),
                userRegistrationDto.getDepartment(), Collections.singletonList(address.toString()));

    }


	@Test
	public void should_return_MonoUser_not_null() throws ResourceNotFoundException {
        when(userService.getById(anyLong())).thenReturn(Mono.just(user));

        webTestClient.get()
                .uri(getRootUrl() + "/users/{userId}",1L)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .value(response-> {
                    assertEquals(user.getFirstName(), response.userInfos.firstName);
                    assertEquals(user.getDepartment().getName(), response.userInfos.getDepartment());
                    assertEquals(user.getAddresses().toString(), response.userInfos.getAdresses().toString());
                });
	}


	@Test
	public void should_create_user_and_department() throws ResourceNotSavedException {
        when(userService.registerUser(any())).thenReturn(Mono.just(user));

        webTestClient.post()
        .uri(getRootUrl() + "/users/register")
        .body(Mono.just(userRegistrationDto), UserRegistrationDto.class)
        .header(HttpHeaders.ACCEPT, "application/json")
        .exchange()
        .expectStatus().isCreated()
        .expectBody(UserDto.class)
        .value(response-> {
            assertEquals(user.getFirstName(), response.userInfos.firstName);
            assertEquals(user.getDepartment().getName(), response.userInfos.getDepartment());
            assertEquals(user.getAddresses().toString(), response.userInfos.getAdresses().toString());
        });

	}

    @Test
    public void should_Throw_user_not_found_exception() throws ResourceNotFoundException {
        when(userService.getById(anyLong())).thenReturn(Mono.error(new ResourceNotFoundException(User.class, new HashMap<>(Collections.singletonMap("id", "1")))));

        webTestClient.get()
                .uri(getRootUrl() + "/users/{userId}",1L)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorDetails.class)
                .value(response-> {
                    assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
                    assertEquals("User was not found for parameter {id=1}", response.getMessage());
                });
    }

    @Test
    public void should_Throw_user_not_saved_exception() throws ResourceNotSavedException {
        when(userService.registerUser(any(User.class))).thenReturn(
                Mono.error(new ResourceNotSavedException(User.class, userRegistrationDto)));

        webTestClient.post()
                .uri(getRootUrl() + "/users/register")
                .body(Mono.just(userRegistrationDto), UserRegistrationDto.class)
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_ACCEPTABLE)
                .expectBody(ErrorDetails.class)
                .value(response-> {
                    assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatus());
                    assertEquals("User was not saved for the object "+
                            userRegistrationDto.toString(), response.getMessage());
                });
    }

    @Test
    public void testMapperUserToDto() {
        UserDto userDto = UserMapper.mapToUserDto(user);
        System.out.println(userDto);
        assertNotNull(userDto.userInfos);
        assertNotNull(userDto.userInfos.adresses);
        assertEquals(userDto.userInfos.adresses.get(0), "20, Rue de Voltaire, Paris, 75015, France");
    }
}
