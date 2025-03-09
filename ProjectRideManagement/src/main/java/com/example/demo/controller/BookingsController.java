package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BookingsDTO;
import com.example.demo.dto.FeedbackDTO;
import com.example.demo.dto.RideHistoryDTO;
import com.example.demo.entity.Bookings;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.BookingsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/bookings")
public class BookingsController {

	private final BookingsService bookingsService;

	public BookingsController(BookingsService bookingsService) {
		this.bookingsService = bookingsService;
	}

	/**
	 * Creates a new booking.
	 * 
	 * @param bookingsDTO the booking data transfer object
	 * @return the ID of the created booking
	 * @throws ResourceNotFoundException if the ride schedule is not found
	 */
	@PostMapping("/book")
	public int getBookingId(@Valid @RequestBody BookingsDTO bookingsDTO) throws ResourceNotFoundException {
		BookingsDTO bDTO = bookingsService.insertBookings(bookingsDTO);
		return bDTO.getId();
	}

	/**
	 * Endpoint to retrieve bookings by ride schedule ID.
	 * 
	 * @param rideScheduleId the ID of the ride schedule
	 * @return a list of BookingsDTO objects
	 */
	@GetMapping("/getbyride/{rideScheduleId}")
	List<BookingsDTO> findByRideSchedulesId(@PathVariable int rideScheduleId) {
		return bookingsService.findByRideSchedulesId(rideScheduleId);
	}

	/**
	 * Deletes the ride history for a specific user.
	 * 
	 * @param userId the ID of the user whose history is to be deleted
	 * @return a confirmation message
	 */
	@DeleteMapping(value = "/clearHistory/{userId}")
	public String deleteHistory(@PathVariable int userId) {
		return bookingsService.deleteHistory(userId);
	}

	/**
	 * Retrieves the ride history for a specific user within a date range.
	 * 
	 * @param userId    the ID of the user
	 * @param startDate the start date of the range
	 * @param endDate   the end date of the range
	 * @return a list of ride history data transfer objects
	 */
	@GetMapping(value = "/getByDate/{userId}")
	public List<RideHistoryDTO> getByDate(@PathVariable int userId, @RequestParam LocalDate startDate,
			@RequestParam LocalDate endDate) {
		return bookingsService.getByDate(userId, startDate, endDate);
	}

	@PostMapping(value = "/submit/{bookingId}")
	public FeedbackDTO submitFeedback(@PathVariable int bookingId, @RequestBody FeedbackDTO feedbackDTO) {
		return bookingsService.submitFeedback(bookingId, feedbackDTO);
	}

	@GetMapping("/{bookingId}")
	public Optional<Bookings> getBookingById(@PathVariable int bookingId) {
		Optional<Bookings> bookingDTO = bookingsService.getBookingById(bookingId);
		return (bookingDTO);
	}

}