package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.entity.UserInfo;
import com.repository.UserInfoRepository;

@Service
public class UserService {
	@Autowired
	private UserInfoRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public String addUser(UserInfo userInfo) {
		String name = userInfo.getName();
		String email = userInfo.getEmail();
		
		UserInfo existingUserByName = repository.findByName(name).orElse(null);
        UserInfo existingUserByEmail = repository.findByEmail(email).orElse(null);
        
        if (existingUserByName != null) {
            throw new RuntimeException("Username is Already Registered.");
        }
        if (existingUserByEmail != null) {
            throw new RuntimeException("Email is Already Registered.");
        }

        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "Registration Successful ";
	}

	public String getRoles(String username) {
		UserInfo obj2 = repository.findByName(username).orElse(null);
		if (obj2 != null) {
			return obj2.getRoles();
		}
		return "Not Found";
	}
}
