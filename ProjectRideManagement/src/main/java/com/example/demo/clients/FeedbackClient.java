package com.example.demo.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.dto.FeedbackDTO;

@FeignClient(name = "feedback-service", url = "http://localhost:3333/feedback")
public interface FeedbackClient {
	@PostMapping("/submit")
	FeedbackDTO submitFeedback(@RequestBody FeedbackDTO feedbackDTO);
}
