package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dto.RideSchedulesDTO;
import com.example.demo.entity.Distances;
import com.example.demo.entity.RideSchedules;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.RideSchedulesMapper;
import com.example.demo.repository.DistancesRepository;
import com.example.demo.repository.RideSchedulesRepository;
import com.example.demo.service.RideSchedulesServiceImpl;

@SpringBootTest
class RideSchedulesTest {
	@Mock
	private DistancesRepository distancesRepository;

	@Mock
	private RideSchedulesRepository rideSchedulesRepository;

	@Mock
	private RideSchedulesMapper rsMapper;

	@InjectMocks
	private RideSchedulesServiceImpl rideSchedulesService;

	private RideSchedulesDTO rideSchedulesDTO;
	private RideSchedules rideSchedules;
	private Distances distances;

	@BeforeEach
	void setUp() {
		rideSchedulesDTO = new RideSchedulesDTO();
		rideSchedulesDTO.setRideFrom("PlaceA");
		rideSchedulesDTO.setRideTo("PlaceB");
		rideSchedulesDTO.setRidedateTime(LocalDateTime.now().plusDays(1));
		rideSchedulesDTO.setRideFare(1000);
		rideSchedulesDTO.setVehicleRegistrationNo("ABC123");
		rideSchedulesDTO.setMotoristUserId(1);
		rideSchedulesDTO.setNoOfSeatsAvailable(4);

		rideSchedules = new RideSchedules();
		rideSchedules.setRideFrom("PlaceA");
		rideSchedules.setRideTo("PlaceB");
		rideSchedules.setRideStartsOn(LocalDate.now().plusDays(1));
		rideSchedules.setRideTime(LocalTime.now().plusHours(1));
		rideSchedules.setRideFare(1000);
		rideSchedules.setVehicleRegistrationNo("ABC123");
		rideSchedules.setMotoristUserId(1);
		rideSchedules.setNoOfSeatsAvailable(4);

		distances = new Distances();
		distances.setFrom("PlaceA");
		distances.setTo("PlaceB");
		distances.setDistanceInKMS(100);
	}

	@Test
	void testInsertRideSchedules() {
		when(distancesRepository.findByFromAndTo("PlaceA", "PlaceB")).thenReturn(Optional.of(distances));
		when(rsMapper.toRideSchedules(rideSchedulesDTO)).thenReturn(rideSchedules);
		when(rideSchedulesRepository.save(rideSchedules)).thenReturn(rideSchedules);
		when(rsMapper.toRideSchedulesDTO(rideSchedules)).thenReturn(rideSchedulesDTO);

		RideSchedulesDTO result = rideSchedulesService.insertRideSchedules(rideSchedulesDTO);

		verify(rideSchedulesRepository).save(rideSchedules);
		assertEquals(rideSchedulesDTO, result);
	}

	@Test
	void testInsertRideSchedules_DistanceNotFound() {
		when(distancesRepository.findByFromAndTo("PlaceA", "PlaceB")).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> {
			rideSchedulesService.insertRideSchedules(rideSchedulesDTO);
		});
	}
}
