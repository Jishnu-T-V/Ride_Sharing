package com.example.demo.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideHistoryDTO {
	
	private int bookingid;

	@NotNull(message = "User ID cannot be null")
	@Min(value = 1, message = "User ID must be greater than 0")
	private int userId;

	@NotNull(message = "Ride date cannot be null")
	private LocalDate rideDate;

	@NotBlank(message = "Ride from location cannot be blank")
	private String ridefrom;

	@NotBlank(message = "Ride to location cannot be blank")
	private String rideto;

	@NotNull(message = "Amount cannot be null")
	@Positive(message = "Amount must be positive")
	private int amount;
}
