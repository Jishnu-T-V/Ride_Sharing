package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.RideSchedulesDTO;
import com.example.demo.dto.SearchCriteriaDTO;
import com.example.demo.exception.ResourceNotFoundException;

@Service
public interface RideSchedulesService {
	RideSchedulesDTO insertRideSchedules(RideSchedulesDTO rideSchedulesdto);

	List<RideSchedulesDTO> searchRide(SearchCriteriaDTO searchCriteriaDTO);

	void updateAvailableSeats(int rideScheduleId, int seatsToBook) throws ResourceNotFoundException;

}
