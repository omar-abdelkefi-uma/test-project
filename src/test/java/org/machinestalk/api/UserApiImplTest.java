package org.machinestalk.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.machinestalk.api.dto.AddressDto;
import org.machinestalk.api.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserApiImplTest {

	@Autowired
	private RestTemplate restTemplate;

	private String baseUrl;

	@BeforeEach
	void setUp() {
		// Assuming that the base URL of your API is localhost and the port is random
		baseUrl = "http://localhost:8080/users"; // Adjust URL accordingly
	}

	@Test
	void shouldRegisterUser() throws Exception {
		// Given
		String url = baseUrl + "/register";

		AddressDto addressDto = new AddressDto();
		addressDto.setStreetNumber("20");
		addressDto.setStreetName("Rue de Voltaire");
		addressDto.setPostalCode("75015");
		addressDto.setCity("Paris");
		addressDto.setCountry("France");

		UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
		userRegistrationDto.setFirstName("Jack");
		userRegistrationDto.setLastName("Sparrow");
		userRegistrationDto.setDepartment("HR");
		userRegistrationDto.setPrincipalAddress(addressDto);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<UserRegistrationDto> request = new HttpEntity<>(userRegistrationDto, headers);

		// When
		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

		// Then
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());

		// You can further parse and check the response body
		String response = responseEntity.getBody();
		assertTrue(response.contains("Jack"));
		assertTrue(response.contains("Sparrow"));
	}

	@Test
	void shouldRetrieveUserById() throws Exception {
		// Given
		String url = baseUrl + "/12345"; // Replace with a valid user ID
		// Assuming you have a user with ID 12345 in your database

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> request = new HttpEntity<>(null, headers);

		// When
		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());

		// Check the response body
		String response = responseEntity.getBody();
		assertTrue(response.contains("firstName"));
		assertTrue(response.contains("lastName"));
		assertTrue(response.contains("department"));
	}
}