package com.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserService service;
    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private UserInfoRepository repo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")		//http://localhost:9090/auth/welcome
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/new")    //http://localhost:9090/auth/new
    public ResponseEntity<Map<String, String>> addNewUser(@RequestBody UserInfo userInfo) {
    	try {
            String result = service.addUser(userInfo);
            Map<String, String> response = new HashMap<>();
            response.put("message", result);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping("/authenticate")        //http://localhost:9090/auth/authenticate
    public ResponseEntity<Map<String, String>> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            UserInfo obj = repo.findByName(authRequest.getUsername()).orElse(null);
            String token = jwtService.generateToken(authRequest.getUsername(), obj.getRoles());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("role", obj.getRoles());
            response.put("id", String.valueOf(obj.getId()));
            return ResponseEntity.ok(response);
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }
    
    @GetMapping("/getroles/{username}")		//http://localhost:9090/auth/getroles/{username}
    public String getRoles(@PathVariable String username)
    {
    	return service.getRoles(username);
    }
}
