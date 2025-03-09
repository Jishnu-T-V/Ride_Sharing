package com.example.demo.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideHistoryDTO {
	
	private int bookingid;

	private int userId;

	private LocalDate rideDate;

	private String ridefrom;

	private String rideto;

	private int amount;
	
	
}
