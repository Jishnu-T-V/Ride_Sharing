package com.example.demo.dto;

import lombok.Data;

@Data
public class DistancesDTO {

	private int id;

	private String from;

	private String to;

	private Integer distanceInKMS;
}
