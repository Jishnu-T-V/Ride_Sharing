package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.FeedbackDTO;
import com.example.demo.entity.Feedback;
import com.example.demo.exception.ResourceNotFoundException;

@Service
public interface FeedbackService {
	public FeedbackDTO saveFeedback(FeedbackDTO feedbackDTO);

	public List<Feedback> getFeedbackForMotorist(int motoristId) throws ResourceNotFoundException;

	public List<Feedback> getFeedbackByRider(int riderId) throws ResourceNotFoundException;
}
