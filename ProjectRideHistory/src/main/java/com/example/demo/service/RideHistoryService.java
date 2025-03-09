package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.RideHistoryDTO;
import com.example.demo.entity.RideHistory;
import com.example.demo.exception.ResourceNotFoundException;

@Service
public interface RideHistoryService {
	public RideHistoryDTO addRideHistory(RideHistoryDTO rideHistoryDto);

	public String deleteHistory(int userId) throws ResourceNotFoundException;

	List<RideHistoryDTO> getByDate(int userId, LocalDate startDate, LocalDate endDate) throws ResourceNotFoundException;
	
	List<RideHistory> findByUserId(int userId);
}
