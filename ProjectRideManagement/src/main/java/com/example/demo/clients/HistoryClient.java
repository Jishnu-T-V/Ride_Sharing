package com.example.demo.clients;

import java.time.LocalDate;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.RideHistoryDTO;

@FeignClient(name = "PROJECTRIDEHISTORY")
public interface HistoryClient {

	@PostMapping(value = "/history/addHistory")
	public RideHistoryDTO addRideHistory(@RequestBody RideHistoryDTO rideHistoryDto);

	@DeleteMapping(value = "/history/clearHistory/{userId}")
	public String deleteHistory(@PathVariable int userId);

	@GetMapping(value = "/history/getByDate/{userId}")
	public List<RideHistoryDTO> getByDate(@PathVariable int userId, @RequestParam LocalDate startDate,
			@RequestParam LocalDate endDate);

}
