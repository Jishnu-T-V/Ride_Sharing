package com.controller;

import java.util.HashMap;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.AuthRequest;
import com.entity.UserInfo;
import com.repository.UserInfoRepository;
import com.service.JwtService;
import com.service.UserService;

@RestController
@RequestMapping("/auth")
//@CrossOrigin("*")
public class AuthController {
	
	private static final String MESSAGE_KEY = "message";

    private final UserService service;
    private final JwtService jwtService;
    private final UserInfoRepository repo;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService service, JwtService jwtService, UserInfoRepository repo, AuthenticationManager authenticationManager) {
        this.service = service;
        this.jwtService = jwtService;
        this.repo = repo;
        this.authenticationManager = authenticationManager;
    }

	@GetMapping("/welcome") 
	public String welcome() {
		return "Welcome this endpoint is not secure";
	}

	@PostMapping("/new") 
	public ResponseEntity<Map<String, String>> addNewUser(@RequestBody UserInfo userInfo) {
		try {
			String result = service.addUser(userInfo);
			Map<String, String> response = new HashMap<>();
			response.put(MESSAGE_KEY, result);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (RuntimeException e) {
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put(MESSAGE_KEY, e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}

	@PostMapping("/authenticate") 
	public ResponseEntity<Map<String, String>> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		if (authentication.isAuthenticated()) {
			UserInfo obj = repo.findByName(authRequest.getUsername()).orElse(null);
			if (obj != null) { 
				String token = jwtService.generateToken(authRequest.getUsername(), obj.getRoles());
				Map<String, String> response = new HashMap<>();
				response.put("token", token);
				response.put("role", obj.getRoles());
				response.put("id", String.valueOf(obj.getId()));
				return ResponseEntity.ok(response);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(MESSAGE_KEY, "User not found")); 
			}
		} else {
			throw new UsernameNotFoundException("invalid user request !");
		}
	}

	@GetMapping("/getroles/{username}") 
	public String getRoles(@PathVariable String username) {
		return service.getRoles(username);
	}
}
