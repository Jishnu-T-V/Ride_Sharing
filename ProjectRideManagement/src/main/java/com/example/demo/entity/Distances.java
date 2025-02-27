package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Distances")
public class Distances {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Distances_Id")
	private int id;

	@Column(name = "From_Place")
	private String from;

	@Column(name = "To_Place")
	private String to;

	@Column(name = "Distance_In_KMS")
	private int distanceInKMS;
}
