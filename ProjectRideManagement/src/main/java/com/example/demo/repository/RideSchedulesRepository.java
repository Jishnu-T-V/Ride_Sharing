package com.example.demo.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.RideSchedules;

@Repository
public interface RideSchedulesRepository extends JpaRepository<RideSchedules, Integer> {
	ArrayList<RideSchedules> findByRideFromAndRideTo(String rideFrom, String rideTo);
	ArrayList<RideSchedules> findByMotoristUserId(int motoristid);
}


