package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.DistancesDTO;

@Service
public interface DistancesService {
	List<DistancesDTO> getAllDistance();
}
