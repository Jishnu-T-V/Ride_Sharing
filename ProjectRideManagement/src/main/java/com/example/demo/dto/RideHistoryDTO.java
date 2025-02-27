package com.example.demo.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideHistoryDTO {

	private int userId;

	private LocalDate rideDate;

	private String from;

	private String to;

	private int amount;
}
