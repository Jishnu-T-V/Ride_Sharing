package com.example.demo.controller;

import java.util.ArrayList;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.RideSchedulesDTO;
import com.example.demo.dto.SearchCriteriaDTO;
import com.example.demo.entity.RideSchedules;
import com.example.demo.exception.SameFromAndToException;
import com.example.demo.service.RideSchedulesService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/rides")
public class RideSchedulesController {

	private final RideSchedulesService rideSchedulesService;

	public RideSchedulesController(RideSchedulesService rideSchedulesService) {
		this.rideSchedulesService = rideSchedulesService;
	}

	/**
	 * Creates a new ride schedule.
	 * 
	 * @param rideSchedulesDTO the ride schedule data transfer object
	 * @return the created ride schedule data transfer object
	 */
	@PostMapping(value = "/schedule")
	public ResponseEntity<?> createNewRide(@Valid @RequestBody RideSchedulesDTO rideSchedulesDTO) {
		if(rideSchedulesDTO.getRideFrom().equals(rideSchedulesDTO.getRideTo())) {
			throw new SameFromAndToException("From and To places cannot be same!!!");
		}
		RideSchedulesDTO createdRide = rideSchedulesService.insertRideSchedules(rideSchedulesDTO);
		return ResponseEntity.ok(createdRide);
	}

	/**
	 * Searches for ride schedules based on search criteria.
	 * 
	 * @param to             the destination location
	 * @param from           the starting location
	 * @param availableSeats the number of available seats
	 * @return a list of ride schedule data transfer objects that match the search
	 *         criteria
	 */
	@GetMapping("/search")
	public List<RideSchedulesDTO> searchRide(@RequestParam("to") String to, @RequestParam("from") String from,
			@RequestParam("availableSeats") int availableSeats) {
		SearchCriteriaDTO searchCriteriaDTO = new SearchCriteriaDTO();
		searchCriteriaDTO.setTo(to);
		searchCriteriaDTO.setFrom(from);
		searchCriteriaDTO.setAvailableSeats(availableSeats);
		

		return rideSchedulesService.searchRide(searchCriteriaDTO);

	}

	@GetMapping("/ridesbymotorist/{motoristid}")
	public ArrayList<RideSchedules> findByMotoristUserId(@PathVariable int motoristid) {
		return rideSchedulesService.findByMotoristUserId(motoristid);
	}
}
