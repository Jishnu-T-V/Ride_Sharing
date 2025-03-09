package com.example.demo.dto;

import lombok.Data;

@Data
public class SearchCriteriaDTO {

	private String from;

	private String to;

	private Integer availableSeats;
	
	private Integer rideFare;
}
