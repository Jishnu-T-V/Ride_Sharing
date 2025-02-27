package com.example.demo.mapper;

import org.springframework.stereotype.Service;

import com.example.demo.dto.DistancesDTO;
import com.example.demo.entity.Distances;

@Service
public class DistanceMapper {
	public DistancesDTO toDistanceDTO(Distances distance) {
		DistancesDTO distancedto = new DistancesDTO();

		distancedto.setId(distance.getId());
		distancedto.setFrom(distance.getFrom());
		distancedto.setTo(distance.getTo());
		distancedto.setDistanceInKMS(distance.getDistanceInKMS());

		return distancedto;
	}
}
