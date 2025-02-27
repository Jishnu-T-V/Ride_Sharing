package com.example.demo.dto;

import lombok.Data;

@Data
public class FeedbackDTO {
	private int rideId;
	private int riderId;
	private int bookingId;
	private int motoristId;
	private int rating;
	private String comments;
}
