package com.example.demo.mapper;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.example.demo.dto.RideSchedulesDTO;
import com.example.demo.entity.RideSchedules;

@Service
public class RideSchedulesMapper {
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

	public RideSchedulesDTO toRideSchedulesDTO(RideSchedules rs) {
		RideSchedulesDTO rideSchedulesdto = new RideSchedulesDTO();

		rideSchedulesdto.setId(rs.getId());
		rideSchedulesdto.setRideFrom(rs.getRideFrom());
		rideSchedulesdto.setRideTo(rs.getRideTo());
		rideSchedulesdto.setRidedateTime(rs.getRideStartsOn().atTime(rs.getRideTime()));
		rideSchedulesdto.setRideFare(rs.getRideFare());
		rideSchedulesdto.setVehicleRegistrationNo(rs.getVehicleRegistrationNo());
		rideSchedulesdto.setMotoristUserId(rs.getMotoristUserId());
		rideSchedulesdto.setNoOfSeatsAvailable(rs.getNoOfSeatsAvailable());

		return rideSchedulesdto;
	}

	public RideSchedules toRideSchedules(RideSchedulesDTO rsdto) {
		RideSchedules rideSchedules = new RideSchedules();

		rideSchedules.setId(rsdto.getId());
		rideSchedules.setRideFrom(rsdto.getRideFrom());
		rideSchedules.setRideTo(rsdto.getRideTo());
		rideSchedules.setRideStartsOn(rsdto.getRidedateTime().toLocalDate());
		rideSchedules.setRideTime(LocalTime.parse(rsdto.getRidedateTime().format(formatter)));
		rideSchedules.setRideFare(rsdto.getRideFare());
		rideSchedules.setVehicleRegistrationNo(rsdto.getVehicleRegistrationNo());
		rideSchedules.setMotoristUserId(rsdto.getMotoristUserId());
		rideSchedules.setNoOfSeatsAvailable(rsdto.getNoOfSeatsAvailable());

		return rideSchedules;
	}
}
