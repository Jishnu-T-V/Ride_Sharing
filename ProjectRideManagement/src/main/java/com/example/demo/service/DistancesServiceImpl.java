package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.DistancesDTO;
import com.example.demo.entity.Distances;
import com.example.demo.mapper.DistanceMapper;
import com.example.demo.repository.DistancesRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class DistancesServiceImpl implements DistancesService {

	private final DistanceMapper distanceMapper;
	private final DistancesRepository distancesRepository;

	@Override
	public List<DistancesDTO> getAllDistance() {

		List<Distances> distances = distancesRepository.findAll();
		List<DistancesDTO> distancedto = new ArrayList<>();
		for (Distances d : distances) {
			DistancesDTO disDto = distanceMapper.toDistanceDTO(d);
			distancedto.add(disDto);
		}
		return distancedto;
	}
}
