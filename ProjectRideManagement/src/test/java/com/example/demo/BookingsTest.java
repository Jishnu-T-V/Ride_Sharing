package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.clients.HistoryClient;
import com.example.demo.dto.BookingsDTO;
import com.example.demo.dto.RideHistoryDTO;
import com.example.demo.entity.Bookings;
import com.example.demo.entity.RideSchedules;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.BookingsMapper;
import com.example.demo.repository.BookingsRepository;
import com.example.demo.repository.RideSchedulesRepository;
import com.example.demo.service.BookingsServiceImpl;
import com.example.demo.service.RideSchedulesServiceImpl;

@SpringBootTest
class BookingsTest {
	@Mock
	private HistoryClient client;

	@Mock
	private RideSchedulesRepository rideSchedulesRepository;

	@Mock
	private BookingsMapper bMapper;

	@Mock
	private BookingsRepository bRepository;

	@Mock
	private RideSchedulesServiceImpl rideSchedulesService;

	@InjectMocks
	private BookingsServiceImpl bookingsService;

	private Bookings bookings;
	private BookingsDTO bookingsDTO;
	private RideSchedules rideSchedules;

	@BeforeEach
	void setUp() {
		rideSchedules = new RideSchedules();
		rideSchedules.setRideStartsOn(LocalDate.now());
		rideSchedules.setRideFrom("A");
		rideSchedules.setRideTo("B");
		rideSchedules.setRideTime(LocalTime.now());
		rideSchedules.setRideFare(100);
		rideSchedules.setVehicleRegistrationNo("ABC123");
		rideSchedules.setMotoristUserId(1);
		rideSchedules.setNoOfSeatsAvailable(4);

		bookings = new Bookings();
		bookings.setBookingid(1);
		bookings.setBookedOn(LocalDate.now());
		bookings.setRiderUserId(1);
		bookings.setNoOfSeats(2);
		bookings.setTotalAmount(200);
		bookings.setPaymentMode("Cash");
		bookings.setRideSchedules(rideSchedules);

		bookingsDTO = new BookingsDTO();
		bookingsDTO.setRideSchedulesId(1);
		bookingsDTO.setRiderUserId(1);
		bookingsDTO.setNoOfSeats(2);
		bookingsDTO.setTotalAmount(200);
	}

	@Test
	void testInsertBookings() throws ResourceNotFoundException {
		when(rideSchedulesRepository.findById(any(Integer.class))).thenReturn(java.util.Optional.of(rideSchedules));
		when(bMapper.toBookings(any(BookingsDTO.class))).thenReturn(bookings);
		when(bRepository.save(any(Bookings.class))).thenReturn(bookings);
		when(bMapper.toBookingsdto(any(Bookings.class))).thenReturn(bookingsDTO);

		BookingsDTO result = bookingsService.insertBookings(bookingsDTO);

		verify(rideSchedulesService).updateAvailableSeats(bookingsDTO.getRideSchedulesId(), bookingsDTO.getNoOfSeats());
		verify(client).addRideHistory(any(RideHistoryDTO.class));
		verify(bRepository).save(bookings);
		assertEquals(bookingsDTO, result);
	}

	@Test
	void testDeleteHistory() {
		int userId = 1;

		String result = bookingsService.deleteHistory(userId);

		verify(client).deleteHistory(userId);
		assertEquals("History Cleared!!!", result);
	}

	@Test
	void testGetByDate() {
		int userId = 1;
		LocalDate startDate = LocalDate.now().minusDays(1);
		LocalDate endDate = LocalDate.now();
		List<RideHistoryDTO> rideHistoryDTOs = Arrays.asList(new RideHistoryDTO());

		when(client.getByDate(userId, startDate, endDate)).thenReturn(rideHistoryDTOs);

		List<RideHistoryDTO> result = bookingsService.getByDate(userId, startDate, endDate);

		verify(client).getByDate(userId, startDate, endDate);
		assertEquals(rideHistoryDTOs, result);
	}

}
