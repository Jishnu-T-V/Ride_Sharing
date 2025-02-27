package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dto.FeedbackDTO;
import com.example.demo.entity.Feedback;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.FeedbackRepository;
import com.example.demo.service.FeedbackServiceImpl;

@SpringBootTest
class ProjectFeedbackApplicationTests {
	@Mock
	private FeedbackRepository feedbackRepository;

	@InjectMocks
	private FeedbackServiceImpl feedbackService;

	private Feedback feedback;
	private FeedbackDTO feedbackDTO;

	@BeforeEach
	public void setUp() {
		feedbackDTO = new FeedbackDTO();
		feedbackDTO.setRideId(1);
		feedbackDTO.setBookingId(1);
		feedbackDTO.setRiderId(1);
		feedbackDTO.setMotoristId(1);
		feedbackDTO.setRating(5);
		feedbackDTO.setComments("Great ride!");

		feedback = new Feedback();
		feedback.setRideId(feedbackDTO.getRideId());
		feedback.setBookingId(feedbackDTO.getBookingId());
		feedback.setRiderId(feedbackDTO.getRiderId());
		feedback.setMotoristId(feedbackDTO.getMotoristId());
		feedback.setRating(feedbackDTO.getRating());
		feedback.setComments(feedbackDTO.getComments());
		feedback.setTimestamp(LocalDateTime.now());
	}

	@Test
	void testSaveFeedback() {

		when(feedbackRepository.save(feedback)).thenReturn(feedback);

		FeedbackDTO result = feedbackService.saveFeedback(feedbackDTO);
		assertEquals(feedbackDTO, result);
	}

	@Test
	void testGetFeedbackForMotorist_NotFound() {
		int motoristId = 8;
		when(feedbackRepository.findByMotoristId(motoristId)).thenReturn(Collections.emptyList());

		assertThrows(ResourceNotFoundException.class, () -> {
			feedbackService.getFeedbackForMotorist(motoristId);
		});
	}

	@Test
	void testGetFeedbackForMotorist_Found() throws ResourceNotFoundException {
		int motoristId = 1;
		feedback = new Feedback();
		feedback.setMotoristId(motoristId);
		List<Feedback> feedbackList = List.of(feedback);

		when(feedbackRepository.findByMotoristId(motoristId)).thenReturn(feedbackList);

		List<Feedback> result = feedbackService.getFeedbackForMotorist(motoristId);
		assertEquals(feedbackList, result);
	}

	@Test
	void testGetFeedbackByRider_NotFound() {
		int riderId = 5;
		when(feedbackRepository.findByRiderId(riderId)).thenReturn(Collections.emptyList());

		assertThrows(ResourceNotFoundException.class, () -> {
			feedbackService.getFeedbackByRider(riderId);
		});
	}

	@Test
	void testGetFeedbackByRider_Found() throws ResourceNotFoundException {
		int riderId = 1;
		feedback = new Feedback();
		feedback.setRiderId(riderId);
		List<Feedback> feedbackList = List.of(feedback);

		when(feedbackRepository.findByRiderId(riderId)).thenReturn(feedbackList);

		List<Feedback> result = feedbackService.getFeedbackByRider(riderId);
		assertEquals(feedbackList, result);
	}
}
