package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Entity
@Table(name = "Ride_Schedules")
public class RideSchedules {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Ride_Schedules_Id")
	private int id;

	@Column(name = "Ride_From")
	@NotBlank(message = "Ride from location cannot be blank")
	private String rideFrom;

	@Column(name = "Ride_To")
	@NotBlank(message = "Ride to location cannot be blank")
	private String rideTo;

	@Column(name = "Ride_Starts_On")
	@NotNull(message = "Ride start date cannot be null")
	@FutureOrPresent(message = "Ride start date must be in the present or future")
	private LocalDate rideStartsOn;

	@Column(name = "Ride_Time")
	@NotNull(message = "Ride time cannot be null")
	private LocalTime rideTime;

	@Column(name = "Ride_Fare")
	@NotNull(message = "Ride fare cannot be null")
	@Positive(message = "Ride fare must be positive")
	private int rideFare;

	@Column(name = "Vehicle_Registration_No")
	@NotBlank(message = "Vehicle registration number cannot be blank")
	private String vehicleRegistrationNo;

	@Column(name = "Motorist_User_Id")
	@NotNull(message = "Motorist user ID cannot be null")
	@Min(value = 1, message = "Motorist user ID must be greater than 0")
	private int motoristUserId;

	@Column(name = "No_Of_Seats_Available")
	@NotNull(message = "Number of seats available cannot be null")
	private int noOfSeatsAvailable;
}
