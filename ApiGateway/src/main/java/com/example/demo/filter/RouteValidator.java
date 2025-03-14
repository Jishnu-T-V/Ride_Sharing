package com.example.demo.filter;


import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouteValidator {


	public static final String[] OPEN_API_ENDPOINTS = { "/auth/register", "/auth/new", "/auth/validate","/api/distances/all","/api/rides/search", "/eureka" };

	public Predicate<ServerHttpRequest> isSecured = request -> {
		String path = request.getPath().toString();
		for (String endpoint : OPEN_API_ENDPOINTS) {
			if (path.contains(endpoint)) {
				return false; 
			}
		}
		return true; 
	};

	
	

}
