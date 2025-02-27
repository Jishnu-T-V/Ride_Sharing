package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.FeedbackDTO;
import com.example.demo.entity.Feedback;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.FeedbackService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

	private final FeedbackService feedbackService;

	public FeedbackController(FeedbackService feedbackService) {
		this.feedbackService = feedbackService;
	}

	/**
	 * Endpoint to submit feedback.
	 * 
	 * @param feedbackDTO The feedback data transfer object containing feedback
	 *                    details.
	 * @return The saved feedback data transfer object.
	 */
	@PostMapping("/submit")
	public FeedbackDTO submitFeedback(@RequestBody @Valid FeedbackDTO feedbackDTO) {
		return feedbackService.saveFeedback(feedbackDTO);
	}

	/**
	 * Endpoint to get feedback for a specific motorist.
	 * 
	 * @param motoristId The ID of the motorist.
	 * @return A list of feedback for the specified motorist.
	 * @throws ResourceNotFoundException If no feedback is found for the motorist.
	 */
	@GetMapping("/motorist/{motoristId}")
	public List<Feedback> getFeedbackForMotorist(@PathVariable int motoristId) throws ResourceNotFoundException {
		return feedbackService.getFeedbackForMotorist(motoristId);
	}

	/**
	 * Endpoint to get feedback by a specific rider.
	 * 
	 * @param riderId The ID of the rider.
	 * @return A list of feedback by the specified rider.
	 * @throws ResourceNotFoundException If no feedback is found for the rider.
	 */
	@GetMapping("/rider/{riderId}")
	public List<Feedback> getFeedbackByRider(@PathVariable int riderId) throws ResourceNotFoundException {
		return feedbackService.getFeedbackByRider(riderId);
	}

}
