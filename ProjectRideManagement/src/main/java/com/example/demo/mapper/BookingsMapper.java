package com.example.demo.mapper;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.demo.dto.BookingsDTO;
import com.example.demo.entity.Bookings;
import com.example.demo.entity.RideSchedules;

@Service
public class BookingsMapper {
	public BookingsDTO toBookingsdto(Bookings bookings) {

		BookingsDTO bookingsdto = new BookingsDTO();

		bookingsdto.setId(bookings.getBookingid());
		bookingsdto.setBookedOn(LocalDate.now());
		bookingsdto.setRiderUserId(bookings.getRiderUserId());
		bookingsdto.setNoOfSeats(bookings.getNoOfSeats());
		bookingsdto.setTotalAmount(bookings.getTotalAmount());
		bookingsdto.setPaymentMode(bookings.getPaymentMode());
		bookingsdto.setRideSchedulesId(bookings.getRideSchedules().getId());

		return bookingsdto;

	}

	public Bookings toBookings(BookingsDTO bdto) {

		Bookings bookings = new Bookings();

		bookings.setBookingid(bdto.getId());
		bookings.setBookedOn(LocalDate.now());
		bookings.setRiderUserId(bdto.getRiderUserId());
		bookings.setNoOfSeats(bdto.getNoOfSeats());
		bookings.setTotalAmount(bdto.getTotalAmount());
		bookings.setPaymentMode(bdto.getPaymentMode());

		RideSchedules rideSchedules = new RideSchedules();
		rideSchedules.setId(bdto.getRideSchedulesId());

		bookings.setRideSchedules(rideSchedules);

		return bookings;
	}
}
