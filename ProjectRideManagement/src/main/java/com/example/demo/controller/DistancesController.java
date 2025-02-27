package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.DistancesDTO;
import com.example.demo.service.DistancesService;

@RestController
@RequestMapping("/api/distances")
public class DistancesController {

	private final DistancesService distancesService;

	public DistancesController(DistancesService distancesService) {
		this.distancesService = distancesService;
	}

	@GetMapping("/all")
	public List<DistancesDTO> getAllDistances() {
		return distancesService.getAllDistance();
	}
}