package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Ride_History")
public class RideHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "History_Id")
	private int id;

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
	
	@Column(name="booking_id")
    private int bookingId;
}
