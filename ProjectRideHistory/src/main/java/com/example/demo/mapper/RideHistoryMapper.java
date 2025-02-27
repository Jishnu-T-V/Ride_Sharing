package com.example.demo.mapper;

import org.springframework.stereotype.Component;

import com.example.demo.dto.RideHistoryDTO;
import com.example.demo.entity.RideHistory;

@Component
public class RideHistoryMapper {
	public RideHistoryDTO convertToDTO(RideHistory rideHistory) {
		return new RideHistoryDTO(rideHistory.getUserId(), rideHistory.getRideDate(), rideHistory.getRidefrom(),
				rideHistory.getRideto(), rideHistory.getAmount());
	}

	public RideHistory convertToRideHistory(RideHistoryDTO rhDto) {
		RideHistory rideHistory = new RideHistory();
		rideHistory.setUserId(rhDto.getUserId());
		rideHistory.setRideDate(rhDto.getRideDate());
		rideHistory.setRidefrom(rhDto.getFrom());
		rideHistory.setRideto(rhDto.getTo());
		rideHistory.setAmount(rhDto.getAmount());

		return rideHistory;
	}
}
