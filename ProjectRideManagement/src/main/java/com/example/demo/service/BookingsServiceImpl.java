package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.clients.FeedbackClient;
import com.example.demo.clients.HistoryClient;
import com.example.demo.dto.BookingsDTO;
import com.example.demo.dto.FeedbackDTO;
import com.example.demo.dto.RideHistoryDTO;
import com.example.demo.entity.Bookings;
import com.example.demo.entity.RideSchedules;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.BookingsMapper;
import com.example.demo.repository.BookingsRepository;
import com.example.demo.repository.RideSchedulesRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BookingsServiceImpl implements BookingsService {

	private final HistoryClient client;
	private final FeedbackClient feedbackClient;
	private final RideSchedulesRepository rideSchedulesRepository;
	private final BookingsMapper bMapper;
	private final BookingsRepository bRepository;
	private final RideSchedulesServiceImpl rideSchedulesService;

	/**
	 * Inserts a new booking and updates the available seats.
	 * 
	 * @param bookingsdto the booking data transfer object
	 * @return the created booking data transfer object
	 * @throws ResourceNotFoundException if the ride schedule is not found
	 */
	@Override
	public BookingsDTO insertBookings(BookingsDTO bookingsdto) throws ResourceNotFoundException {

		// Update available seats
		rideSchedulesService.updateAvailableSeats(bookingsdto.getRideSchedulesId(), bookingsdto.getNoOfSeats());

		RideSchedules rideSchedule = rideSchedulesRepository.findById(bookingsdto.getRideSchedulesId())
				.orElseThrow(() -> new ResourceNotFoundException("Ride schedule not found"));

		Bookings bookings = bMapper.toBookings(bookingsdto);
		bookings.setMotoristUserId(rideSchedule.getMotoristUserId());

		Bookings createBooking = bRepository.save(bookings);

		// Add to history for rider
		RideHistoryDTO riderHistory = new RideHistoryDTO(createBooking.getBookingid(), bookingsdto.getRiderUserId(), rideSchedule.getRideStartsOn(),
				rideSchedule.getRideFrom(), rideSchedule.getRideTo(), bookingsdto.getTotalAmount());
		client.addRideHistory(riderHistory);

		return bMapper.toBookingsdto(createBooking);

	}

	/**
	 * Deletes the ride history for a specific user.
	 * 
	 * @param userId the ID of the user whose history is to be deleted
	 * @return a confirmation message
	 */
	@Override
	public String deleteHistory(int userId) {
		client.deleteHistory(userId);
		return "History Cleared!!!";
	}

	/**
	 * Retrieves the ride history for a specific user within a date range.
	 * 
	 * @param userId    the ID of the user
	 * @param startDate the start date of the range
	 * @param endDate   the end date of the range
	 * @return a list of ride history data transfer objects
	 */
	@Override
	public List<RideHistoryDTO> getByDate(int userId, LocalDate startDate, LocalDate endDate)
			throws ResourceNotFoundException {
		return client.getByDate(userId, startDate, endDate);

	}

	/**
	 * Retrieves a list of bookings for a given ride schedule ID.
	 * 
	 * @param rideScheduleId the ID of the ride schedule
	 * @return a list of BookingsDTO objects
	 * @throws ResourceNotFoundException if no bookings are found for the given ride
	 *                                   schedule ID
	 */
	@Override
	public List<BookingsDTO> findByRideSchedulesId(int rideScheduleId) {
		List<Bookings> bookings = bRepository.findByRideSchedulesId(rideScheduleId);
		if (bookings.isEmpty()) {
			throw new ResourceNotFoundException("No bookings found for the given ride schedule ID");
		}

		List<BookingsDTO> bookingsDTOList = new ArrayList<>();
		for (Bookings booking : bookings) {
			BookingsDTO bookingsDTO = bMapper.toBookingsdto(booking);
			bookingsDTOList.add(bookingsDTO);
		}
		return bookingsDTOList;
	}

	public FeedbackDTO submitFeedback(int bookingId, FeedbackDTO feedbackDTO) throws ResourceNotFoundException {
		// Retrieve booking information
		Bookings booking = bRepository.findById(bookingId)
				.orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

		// Retrieve ride schedule information
		RideSchedules rideSchedule = booking.getRideSchedules();

		// Create FeedbackDTO with rideId, riderId, and motoristId
		FeedbackDTO completeFeedbackDTO = new FeedbackDTO();
		completeFeedbackDTO.setRideId(rideSchedule.getId());
		completeFeedbackDTO.setBookingId(bookingId);
		completeFeedbackDTO.setRiderId(booking.getRiderUserId());
		completeFeedbackDTO.setMotoristId(rideSchedule.getMotoristUserId());
		completeFeedbackDTO.setRating(feedbackDTO.getRating());
		completeFeedbackDTO.setComments(feedbackDTO.getComments());

		// Submit feedback using FeignClient
		feedbackClient.submitFeedback(completeFeedbackDTO);

		return completeFeedbackDTO;
	}

	@Override
	public Optional<Bookings> getBookingById(int bookingId) {
		return bRepository.findById(bookingId);
	}

}
