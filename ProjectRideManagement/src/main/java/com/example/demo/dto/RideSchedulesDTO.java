package com.example.demo.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RideSchedulesDTO {
	private int id;

	@NotBlank(message = "Ride from location cannot be blank")
	private String rideFrom;

	@NotBlank(message = "Ride to location cannot be blank")
	private String rideTo;

	@NotNull(message = "Ride date and time cannot be null")
	@FutureOrPresent(message = "Ride date and time must be in the present or future")
	private LocalDateTime ridedateTime;

	private Integer rideFare;

	@NotBlank(message = "Vehicle registration number cannot be blank")
	private String vehicleRegistrationNo;

	@NotNull(message = "Motorist user ID cannot be null")
	@Min(value = 1, message = "Motorist user ID must be greater than 0")
	private Integer motoristUserId;

	@NotNull(message = "Number of seats available cannot be null")
	@Positive(message = "Number of seats available must be positive")
	private Integer noOfSeatsAvailable;
}
