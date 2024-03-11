package com.macroz.medalnetserver.controller;

import com.macroz.medalnetserver.dtos.*;
import com.macroz.medalnetserver.model.User;
import com.macroz.medalnetserver.auth.JwtUtil;
import com.macroz.medalnetserver.service.AuthService;
import com.macroz.medalnetserver.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private final AuthService authService;
	private final AuthenticationManager authenticationManager;
	private final UserService userService;

	private final JwtUtil jwtUtil;

	public AuthController(AuthService authService, AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService) {
		this.authService = authService;
		this.authenticationManager = authenticationManager;
		this.userService = userService;
		this.jwtUtil = jwtUtil;
	}

	// login by email (/login/email) or by password (/login/password)
	@PostMapping("/login/{emailOrUsername}")
	public ResponseEntity<?> login(@RequestBody LoginRequestDto loginReq, @PathVariable String emailOrUsername) {
		String usernameOrEmailData;

		if (emailOrUsername.equals("email")) {
			usernameOrEmailData = loginReq.getEmail();
		} else if (emailOrUsername.equals("username")) {
			usernameOrEmailData = loginReq.getUsername();
		} else {
			return ResponseEntity.badRequest()
					.body("Invalid endpoint usage. Please use either /login/email or /login/username.");
		}

		try {
			Authentication authentication =
					authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usernameOrEmailData, loginReq.getPassword()));
			Optional<User> userOptional;
			if (emailOrUsername.equals("email")) {
				userOptional = userService.getByEmail(usernameOrEmailData);
			} else {
				userOptional = userService.getByUsername(usernameOrEmailData);
			}

			User user = userOptional.get();
			user.setPassword(null);
			String token = jwtUtil.createToken(user);
			LoginRes loginRes = new LoginRes(token, user);

			return new ResponseEntity<>(loginRes, HttpStatus.OK);

		}catch (BadCredentialsException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username or password");
		}catch (Exception e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto request) {
		UserDto userDto = authService.signUp(request.getUsername(), request.getEmail(), request.getPassword());
		if (userDto == null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Username and email are already taken");
		} else {
			return new ResponseEntity<>(userDto, HttpStatus.CREATED);
		}
	}


}
