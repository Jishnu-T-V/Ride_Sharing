package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.FeedbackDTO;
import com.example.demo.entity.Feedback;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.FeedbackRepository;

@Service
public class FeedbackServiceImpl implements FeedbackService {
	private final FeedbackRepository feedbackRepository;

	public FeedbackServiceImpl(FeedbackRepository feedbackRepository) {
		this.feedbackRepository = feedbackRepository;
	}

	/**
	 * Saves the feedback provided by the user.
	 * 
	 * @param feedbackDTO The feedback data transfer object containing feedback
	 *                    details.
	 * @return The saved feedback data transfer object.
	 */
	public FeedbackDTO saveFeedback(FeedbackDTO feedbackDTO) {
		Feedback feedback = new Feedback();
		feedback.setRideId(feedbackDTO.getRideId());
		feedback.setBookingId(feedbackDTO.getBookingId());
		feedback.setRiderId(feedbackDTO.getRiderId());
		feedback.setMotoristId(feedbackDTO.getMotoristId());
		feedback.setRating(feedbackDTO.getRating());
		feedback.setComments(feedbackDTO.getComments());
		feedback.setTimestamp(LocalDateTime.now());
		feedbackRepository.save(feedback);
		return feedbackDTO;
	}

	/**
	 * Retrieves feedback for a specific motorist.
	 * 
	 * @param motoristId The ID of the motorist.
	 * @return A list of feedback for the specified motorist.
	 * @throws ResourceNotFoundException If no feedback is found for the motorist.
	 */
	public List<Feedback> getFeedbackForMotorist(int motoristId) throws ResourceNotFoundException {
		List<Feedback> feedbackList = feedbackRepository.findByMotoristId(motoristId);
		if (feedbackList.isEmpty()) {
			throw new ResourceNotFoundException("No feedback found for motorist with ID " + motoristId);
		}
		return feedbackList;
	}

	/**
	 * Retrieves feedback by a specific rider.
	 * 
	 * @param riderId The ID of the rider.
	 * @return A list of feedback by the specified rider.
	 * @throws ResourceNotFoundException If no feedback is found for the rider.
	 */
	public List<Feedback> getFeedbackByRider(int riderId) throws ResourceNotFoundException {
		List<Feedback> feedbackList = feedbackRepository.findByRiderId(riderId);
		if (feedbackList.isEmpty()) {
			throw new ResourceNotFoundException("No feedback found for rider with ID " + riderId);
		}
		return feedbackList;
	}
}
