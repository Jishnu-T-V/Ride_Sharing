package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.RideHistoryDTO;
import com.example.demo.entity.RideHistory;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.RideHistoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/history")
public class RideHistoryController {

	private final RideHistoryService rideHistoryService;

	public RideHistoryController(RideHistoryService rideHistoryService) {
		this.rideHistoryService = rideHistoryService;
	}

	/**
	 * Adds a new ride history record.
	 * 
	 * @param rideHistoryDto the ride history data transfer object
	 * @return the added ride history data transfer object
	 */
	@PostMapping(value = "/addHistory")
	public RideHistoryDTO addRideHistory(@Valid @RequestBody RideHistoryDTO rideHistoryDto) {
		return rideHistoryService.addRideHistory(rideHistoryDto);
	}

	/**
	 * Deletes the ride history for a specific user.
	 * 
	 * @param userId the ID of the user whose history is to be deleted
	 * @return a confirmation message
	 * @throws ResourceNotFoundException if the user ID is not found
	 */
	 @DeleteMapping(value = "/clearHistory/{userId}")
	    public ResponseEntity<Object> deleteHistory(@PathVariable int userId) throws ResourceNotFoundException {
	        rideHistoryService.deleteHistory(userId);
	        return ResponseEntity.ok(Map.of("message", "History Cleared!!!")); // Return JSON
	    }

	/**
	 * Retrieves the ride history for a specific user within a date range.
	 * 
	 * @param userId    the ID of the user
	 * @param startDate the start date of the range
	 * @param endDate   the end date of the range
	 * @return a list of ride history data transfer objects
	 * @throws ResourceNotFoundException if no ride history is found for the user
	 *                                   within the date range
	 */
	@GetMapping(value = "/getByDate/{userId}")
	public List<RideHistoryDTO> getByDate(@PathVariable int userId, @RequestParam LocalDate startDate,
			@RequestParam LocalDate endDate) throws ResourceNotFoundException {

		return rideHistoryService.getByDate(userId, startDate, endDate);
	}
	
	@GetMapping("/getbyuser/{userId}")
	public List<RideHistory> findByUserId(@PathVariable int userId){
		return rideHistoryService.findByUserId(userId);
	}
}
