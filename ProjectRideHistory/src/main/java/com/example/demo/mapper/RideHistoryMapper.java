package com.example.demo.mapper;

import org.springframework.stereotype.Component;

import com.example.demo.dto.RideHistoryDTO;
import com.example.demo.entity.RideHistory;

@Component
public class RideHistoryMapper {
	public RideHistoryDTO convertToDTO(RideHistory rideHistory) {
		return new RideHistoryDTO(rideHistory.getBookingId(),rideHistory.getUserId(), rideHistory.getRideDate(), rideHistory.getRidefrom(),
				rideHistory.getRideto(), rideHistory.getAmount());
	}

	public RideHistory convertToRideHistory(RideHistoryDTO rhDto) {
		RideHistory rideHistory = new RideHistory();
		rideHistory.setBookingId(rhDto.getBookingid());
		rideHistory.setUserId(rhDto.getUserId());
		rideHistory.setRideDate(rhDto.getRideDate());
		rideHistory.setRidefrom(rhDto.getRidefrom());
		rideHistory.setRideto(rhDto.getRideto());
		rideHistory.setAmount(rhDto.getAmount());

		return rideHistory;
	}
}
