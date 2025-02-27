package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.BookingsDTO;
import com.example.demo.dto.FeedbackDTO;
import com.example.demo.dto.RideHistoryDTO;
import com.example.demo.exception.ResourceNotFoundException;

@Service
public interface BookingsService {
	BookingsDTO insertBookings(BookingsDTO bookingsdto) throws ResourceNotFoundException;

	String deleteHistory(int userId);

	List<RideHistoryDTO> getByDate(@PathVariable int userId, @RequestParam LocalDate startDate,
			@RequestParam LocalDate endDate);

	List<BookingsDTO> findByRideSchedulesId(int rideScheduleId);

	public FeedbackDTO submitFeedback(@PathVariable int bookingId, FeedbackDTO feedbackDTO);

}
