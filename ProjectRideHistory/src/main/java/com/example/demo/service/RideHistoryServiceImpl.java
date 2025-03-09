package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.RideHistoryDTO;
import com.example.demo.entity.RideHistory;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.RideHistoryMapper;
import com.example.demo.repository.RideHistoryRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RideHistoryServiceImpl implements RideHistoryService {

	private final RideHistoryRepository rideHistoryRepository;
	private final RideHistoryMapper rhMapper;

	/**
	 * Adds a new ride history record.
	 * 
	 * @param rideHistoryDto the ride history data transfer object
	 * @return the added ride history data transfer object
	 */
	public RideHistoryDTO addRideHistory(RideHistoryDTO rideHistoryDto) {
		RideHistory rideHistory = rhMapper.convertToRideHistory(rideHistoryDto);

		rideHistoryRepository.save(rideHistory);

		return rideHistoryDto;
	}

	/**
	 * Deletes the ride history for a specific user.
	 * 
	 * @param userId the ID of the user whose history is to be deleted
	 * @return a confirmation message
	 * @throws ResourceNotFoundException if the user ID is not found
	 */
	@Override
	@Transactional
	public String deleteHistory(int userId) throws ResourceNotFoundException {
		if (!rideHistoryRepository.existsByUserId(userId)) {
			throw new ResourceNotFoundException("User with ID " + userId + " not found.");
		}
		rideHistoryRepository.deleteByUserId(userId);
		return "History Cleared";
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
	@Override
	public List<RideHistoryDTO> getByDate(int userId, LocalDate startDate, LocalDate endDate)
			throws ResourceNotFoundException {
		List<RideHistory> rideHistories = rideHistoryRepository.findByUserIdAndRideDateBetween(userId, startDate,
				endDate);

		if (rideHistories.isEmpty()) {
			throw new ResourceNotFoundException(
					"No ride history found for user with ID " + userId + " between " + startDate + " and " + endDate);
		}

		List<RideHistoryDTO> rideHistoryDTOs = new ArrayList<>();

		for (RideHistory rideHistory : rideHistories) {
			RideHistoryDTO rideHistoryDTO = rhMapper.convertToDTO(rideHistory);
			rideHistoryDTOs.add(rideHistoryDTO);
		}

		return rideHistoryDTOs;
	}

	@Override
	public List<RideHistory> findByUserId(int userId) {
		return rideHistoryRepository.findByUserId(userId);
	}

}
