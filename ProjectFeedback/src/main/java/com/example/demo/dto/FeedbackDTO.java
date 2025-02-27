package com.example.demo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FeedbackDTO {
	private int rideId;
	private int riderId;
	private int bookingId;
	private int motoristId;

	@Min(value = 1, message = "Rating must be at least 1")
	@Max(value = 5, message = "Rating must be at most 5")
	private int rating;

	@NotBlank(message = "Comments cannot be blank")
	private String comments;
}
