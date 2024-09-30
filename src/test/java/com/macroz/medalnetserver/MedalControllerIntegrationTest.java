package com.macroz.medalnetserver;

import com.macroz.medalnetserver.auth.JwtUtil;
import com.macroz.medalnetserver.model.Medal;
import com.macroz.medalnetserver.model.User;
import com.macroz.medalnetserver.repository.MedalHistoryRepository;
import com.macroz.medalnetserver.repository.MedalRepository;
import com.macroz.medalnetserver.repository.UserRepository;
import com.macroz.medalnetserver.service.MedalService;
import com.macroz.medalnetserver.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MedalControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private MedalRepository medalRepository;

	@Autowired
	private MedalHistoryRepository medalHistoryRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MedalService medalService;

	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtil jwtUtil;

	@BeforeEach
	public void setup() {
		// TODO: Add deleteAll functions to service, and use service here
		medalRepository.deleteAll();
		userRepository.deleteAll();
		medalHistoryRepository.deleteAll();
	}

	@Test
	public void testAddMedal() {
		// TODO: Check if MedalHistory is working correct
		// Create a test user
		User user = new User("testUser", "dummy@mail.com", "password", null);
		userService.createUser(user);

		// Simulate a valid JWT token for the test user
		String validToken = "Bearer " + jwtUtil.createToken(user);

		// Create a Medal object to add
		Medal medal = new Medal(null,
				"12345",
				"John",
				"Doe",
				"General",
				"Army",
				2022,
				"Awarded for bravery",
				null);

		// Build the request entity with the medal data and headers
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", validToken);
		HttpEntity<Medal> request = new HttpEntity<>(medal, headers);

		// Send the POST request to the /medal/add endpoint
		ResponseEntity<Medal> response = restTemplate.postForEntity("/medal/add", request, Medal.class);

		// Check if the response status code is 201 CREATED
		assertEquals(HttpStatus.CREATED, response.getStatusCode());

		// Assert the created medal is not null and contains correct data
		Medal createdMedal = response.getBody();
		assertNotNull(createdMedal);
		assertEquals("12345", createdMedal.getNumber());
		assertEquals("John", createdMedal.getName());
		assertEquals("Doe", createdMedal.getSurname());
		assertEquals("General", createdMedal.getRank());
		assertEquals("Army", createdMedal.getUnit());
		assertEquals(2022, createdMedal.getYear());
		assertEquals("Awarded for bravery", createdMedal.getNotes());
		assertEquals(user.getId(), createdMedal.getUserId());

		// Verify if medal is saved in repository
		Optional<Medal> savedMedal = medalRepository.findMedalById(createdMedal.getId());
		assertNotNull(savedMedal.orElse(null));
	}

	@Test
	public void testGetAllMyMedals() {
		// Create a test user
		User user = new User("testUser", "dummy@email.com", "password", null);
		String validToken = "Bearer " + jwtUtil.createToken(user);
		user = userRepository.save(user);

		// Create a dummy user
		User dummyUser = new User("dummyUser", "dummyUser@email.com", "dummyPassword", null);
		dummyUser = userRepository.save(dummyUser);

		List<Medal> medalList = new ArrayList<>();

		// Add 2 medals for testUser, and 2 medals for dummyUser
		for (int i = 0; i < 2; i++) {
			Medal testMedal = new Medal();
			Medal dummyMedal = new Medal();
			testMedal.setNumber("12345" + i);
			dummyMedal.setNumber("54321" + i);
			testMedal.setName("test_" + String.valueOf(i));
			dummyMedal.setName("dummy_" + String.valueOf(i));

			// Set the user ID for the medals
			testMedal.setUserId(user.getId());
			dummyMedal.setUserId(dummyUser.getId());

			// Add the medals
			medalService.addMedal(testMedal);
			medalService.addMedal(dummyMedal);
			medalList.add(testMedal);
		}

		// Build the request entity
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", validToken);
		HttpEntity<String> request = new HttpEntity<>(headers);

		// Send the GET request to the /medal/my endpoint
		ResponseEntity<Medal[]> response = restTemplate.exchange(
				"/medal/my",
				HttpMethod.GET,
				request,
				Medal[].class
		);

		assertNotNull(response.getBody());
		List<Medal> medals = List.of(response.getBody());
		assertEquals(2, medals.size());

		assertEquals(HttpStatus.OK, response.getStatusCode());

		assertArrayEquals(new Long[]{medals.get(0).getUserId(), medals.get(1).getUserId()},
				new Long[]{user.getId(), user.getId()});
		assertIterableEquals(medalList, medals);
	}
}
