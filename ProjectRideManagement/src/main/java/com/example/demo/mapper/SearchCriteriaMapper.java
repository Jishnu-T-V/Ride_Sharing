package com.example.demo.mapper;

import org.springframework.stereotype.Service;

import com.example.demo.dto.SearchCriteriaDTO;
import com.example.demo.entity.Distances;
import com.example.demo.entity.RideSchedules;

@Service
public class SearchCriteriaMapper {
	public SearchCriteriaDTO toSearchParamterdto(Distances distances, RideSchedules rs) {

		SearchCriteriaDTO scdto = new SearchCriteriaDTO();

		scdto.setFrom(distances.getFrom());
		scdto.setTo(distances.getTo());
		scdto.setAvailableSeats(rs.getNoOfSeatsAvailable());

		return scdto;

	}
}
