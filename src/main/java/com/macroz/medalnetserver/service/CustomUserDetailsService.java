package com.macroz.medalnetserver.service;

import com.macroz.medalnetserver.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;

	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<com.macroz.medalnetserver.model.User> userOptional = userRepository.findByEmail(email);
		if (userOptional.isEmpty()) userOptional = userRepository.findByUsername(email);
		if (userOptional.isEmpty()) throw new BadCredentialsException("Invalid username or password");
		com.macroz.medalnetserver.model.User user = userOptional.get();

		List<String> roles = new ArrayList<>();
		roles.add("USER");
		UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
				.username(user.getUsername())
				.password(user.getPassword())
				.roles(roles.toArray(new String[0]))
				.build();
		return userDetails;
	}
}
