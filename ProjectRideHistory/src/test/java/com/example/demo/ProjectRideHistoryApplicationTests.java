package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.dto.RideHistoryDTO;
import com.example.demo.entity.RideHistory;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.RideHistoryMapper;
import com.example.demo.repository.RideHistoryRepository;
import com.example.demo.service.RideHistoryServiceImpl;

@ExtendWith(MockitoExtension.class)
class ProjectRideHistoryApplicationTests {

	@Mock
	private RideHistoryRepository rideHistoryRepository;

	@Mock
	private RideHistoryMapper rhMapper;

	@InjectMocks
	private RideHistoryServiceImpl rideHistoryService;

	private RideHistory rideHistory;
	private RideHistoryDTO rideHistoryDTO;

	@BeforeEach
	void setUp() {
		rideHistory = new RideHistory(1, 1, LocalDate.now(), "A", "B", 100, 1);
		rideHistoryDTO = new RideHistoryDTO(1, 1, LocalDate.now(), "A", "B", 100);
	}

	@Test
	void testAddRideHistory() {
		when(rhMapper.convertToRideHistory(any(RideHistoryDTO.class))).thenReturn(rideHistory);
		when(rideHistoryRepository.save(any(RideHistory.class))).thenReturn(rideHistory);

		RideHistoryDTO result = rideHistoryService.addRideHistory(rideHistoryDTO);

		verify(rideHistoryRepository).save(rideHistory);
		assertEquals(rideHistoryDTO, result);
	}

	@Test
	void testDeleteHistory_UserNotFound() {
		when(rideHistoryRepository.existsByUserId(1)).thenReturn(false);

		assertThrows(ResourceNotFoundException.class, () -> {
			rideHistoryService.deleteHistory(1);
		});
	}

	@Test
	void testGetByDate() throws ResourceNotFoundException {
		int userId = 1;
		LocalDate startDate = LocalDate.now().minusDays(1);
		LocalDate endDate = LocalDate.now();
		List<RideHistory> rideHistories = Arrays.asList(rideHistory);

		when(rideHistoryRepository.findByUserIdAndRideDateBetween(userId, startDate, endDate))
				.thenReturn(rideHistories);
		when(rhMapper.convertToDTO(any(RideHistory.class))).thenReturn(rideHistoryDTO);

		List<RideHistoryDTO> result = rideHistoryService.getByDate(userId, startDate, endDate);

		verify(rideHistoryRepository).findByUserIdAndRideDateBetween(userId, startDate, endDate);
		assertEquals(1, result.size());
		assertEquals(rideHistoryDTO, result.get(0));
	}
}