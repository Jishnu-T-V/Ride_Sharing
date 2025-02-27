package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.RideSchedulesDTO;
import com.example.demo.dto.SearchCriteriaDTO;
import com.example.demo.entity.Distances;
import com.example.demo.entity.RideSchedules;
import com.example.demo.exception.NoOfSeatsLimitExceedException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.SameFromAndToException;
import com.example.demo.mapper.RideSchedulesMapper;
import com.example.demo.repository.DistancesRepository;
import com.example.demo.repository.RideSchedulesRepository;

import jakarta.transaction.Transactional;

@Service
public class RideSchedulesServiceImpl implements RideSchedulesService {
	
	private final DistancesRepository distancesRepository;
	private final RideSchedulesRepository rideSchedulesRepository;
	private final RideSchedulesMapper rsMapper;

	public RideSchedulesServiceImpl(DistancesRepository distancesRepository,
			RideSchedulesRepository rideSchedulesRepository, RideSchedulesMapper rsMapper) {
		this.distancesRepository = distancesRepository;
		this.rideSchedulesRepository = rideSchedulesRepository;
		this.rsMapper = rsMapper;
	}

	/**
	 * Inserts a new ride schedule and calculates the fare based on the distance.
	 * 
	 * @param rideSchedulesdto the ride schedule data transfer object
	 * @return the created ride schedule data transfer object
	 * @throws ResourceNotFoundException if the distance between the locations is
	 *                                   not found
	 */
	@Override
	public RideSchedulesDTO insertRideSchedules(RideSchedulesDTO rideSchedulesdto) {

		Distances distanceEntity = distancesRepository
				.findByFromAndTo(rideSchedulesdto.getRideFrom(), rideSchedulesdto.getRideTo())
				.orElseThrow(() -> new ResourceNotFoundException("Distance not found"));

		int fare = distanceEntity.getDistanceInKMS() * 10;

		rideSchedulesdto.setRideFare(fare);

		RideSchedules rideSchedule = rsMapper.toRideSchedules(rideSchedulesdto);

		RideSchedules createRideSchedule = rideSchedulesRepository.save(rideSchedule);

		return rsMapper.toRideSchedulesDTO(createRideSchedule);
	}

	/**
	 * Searches for ride schedules based on search criteria.
	 * 
	 * @param searchCriteriaDTO the search criteria data transfer object
	 * @return a list of ride schedule data transfer objects that match the search
	 *         criteria
	 * @throws SameFromAndToException if the starting and destination locations are
	 *                                the same
	 */
	@Override
	public List<RideSchedulesDTO> searchRide(SearchCriteriaDTO searchCriteriaDTO) {

		if (searchCriteriaDTO.getFrom().equals(searchCriteriaDTO.getTo())) {
			throw new SameFromAndToException("From and To places cannot be same");
		}

		distancesRepository.findByFromAndTo(searchCriteriaDTO.getFrom(), searchCriteriaDTO.getTo())
				.orElseThrow(() -> new ResourceNotFoundException("No rides available!!!"));

		ArrayList<RideSchedules> rideSchedulesArrayList = rideSchedulesRepository
				.findByRideFromAndRideTo(searchCriteriaDTO.getFrom(), searchCriteriaDTO.getTo());
		List<RideSchedulesDTO> rideSchedulesDTOList = new ArrayList<>();
		for (RideSchedules rideSchedules : rideSchedulesArrayList) {
			if (rideSchedules.getNoOfSeatsAvailable() >= searchCriteriaDTO.getAvailableSeats()) {
				RideSchedulesDTO rideSchedulesDTO = rsMapper.toRideSchedulesDTO(rideSchedules);
				rideSchedulesDTOList.add(rideSchedulesDTO);
			}
			else {
				throw new ResourceNotFoundException("No rides available!!!");
			}
		}
		return rideSchedulesDTOList;
	}

	/**
	 * Updates the number of available seats for a ride schedule.
	 * 
	 * @param rideScheduleId the ID of the ride schedule
	 * @param seatsToBook    the number of seats to book
	 * @throws ResourceNotFoundException     if the ride schedule is not found
	 * @throws NoOfSeatsLimitExceedException if there are not enough available seats
	 */
	@Override
	@Transactional
	public void updateAvailableSeats(int rideScheduleId, int seatsToBook) throws ResourceNotFoundException {
		RideSchedules rideSchedule = rideSchedulesRepository.findById(rideScheduleId)
				.orElseThrow(() -> new ResourceNotFoundException("Ride schedule not found"));

		if (rideSchedule.getNoOfSeatsAvailable() < seatsToBook) {
			throw new NoOfSeatsLimitExceedException("Not enough available seats");
		}

		rideSchedule.setNoOfSeatsAvailable(rideSchedule.getNoOfSeatsAvailable() - seatsToBook);
		rideSchedulesRepository.save(rideSchedule);
	}
	

}


