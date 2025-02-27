package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Entity
@Table(name = "Bookings")
public class Bookings {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Bookings_Id")
	private int bookingid;

	@Column(name = "Booked_On")
//	@NotNull(message = "Booking date cannot be null")
	@PastOrPresent(message = "Booking date cannot be in the future")
	private LocalDate bookedOn;

	@Column(name = "Rider_User_Id")
	@NotNull(message = "Rider user ID cannot be null")
	@Min(value = 1, message = "Rider user ID must be greater than 0")
	private int riderUserId;

	@Column(name = "No_Of_Seats")
	@NotNull(message = "Number of seats cannot be null")
	private int noOfSeats;

	@Column(name = "Total_Amount")
	@NotNull(message = "Total amount cannot be null")
	@Positive(message = "Total amount must be positive")
	private int totalAmount;

	@Column(name = "Payment_Mode")
	@NotNull(message = "Payment mode cannot be null")
	@NotBlank(message = "Payment mode cannot be blank")
	private String paymentMode;

	@ManyToOne
	@JoinColumn(name = "Ride_Schedules_Id")
	private RideSchedules rideSchedules;
}
