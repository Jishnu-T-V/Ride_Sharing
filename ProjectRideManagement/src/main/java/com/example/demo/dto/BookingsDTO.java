package com.example.demo.dto;

import java.time.LocalDate;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class BookingsDTO {
	private int id;

	private LocalDate bookedOn;

	@NotNull(message = "Rider user ID cannot be null")
	@Min(value = 1, message = "Rider user ID must be greater than 0")
	private Integer riderUserId;

	@NotNull(message = "Number of seats cannot be null")
	private Integer noOfSeats;

	@NotNull(message = "Total amount cannot be null")
	@Positive(message = "Total amount must be positive")
	private Integer totalAmount;

	@NotNull(message = "Payment mode cannot be null")
	@NotBlank(message = "Payment mode cannot be blank")
	private String paymentMode;

	@NotNull(message = "RideSchedules ID cannot be null")
	@Min(value = 1, message = "RideSchedules ID must be greater than 0")
	private Integer rideSchedulesId;
}
