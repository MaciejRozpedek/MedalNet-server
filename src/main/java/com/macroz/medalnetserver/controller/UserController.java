package com.macroz.medalnetserver.controller;

import com.macroz.medalnetserver.auth.JwtUtil;
import com.macroz.medalnetserver.model.User;
import com.macroz.medalnetserver.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
	private final UserService userService;
	private final JwtUtil jwtUtil;

	public UserController(UserService userService, JwtUtil jwtUtil) {
		this.userService = userService;
		this.jwtUtil = jwtUtil;
	}

	@GetMapping("/profile")
	public ResponseEntity<?> getProfile(@RequestHeader HttpHeaders headers) {
		String accessToken = jwtUtil.resolveToken(headers);
		String username = jwtUtil.getUsernameFromToken(accessToken);
		Optional<User> userOptional = userService.getByUsername(username);
		if (userOptional.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		User user = userOptional.get();
		user.setPassword(null);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
}
