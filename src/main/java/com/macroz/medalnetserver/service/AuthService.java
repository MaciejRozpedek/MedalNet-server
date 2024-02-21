package com.macroz.medalnetserver.service;

import com.macroz.medalnetserver.dtos.UserDto;
import com.macroz.medalnetserver.model.User;
import com.macroz.medalnetserver.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public AuthService(UserRepository userRepository) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
	}

	public UserDto signUp(String username, String email, String password) {
		//check if username or email are taken
		Optional<User> userOptional = userRepository.findByUsername(username);
		if (userOptional.isPresent()) return null;
		userOptional = userRepository.findByEmail(email);
		if (userOptional.isPresent()) return null;

		// create new user
		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(bCryptPasswordEncoder.encode(password));

		User savedUser = userRepository.save(user);

		return UserDto.from(savedUser);
	}
}
