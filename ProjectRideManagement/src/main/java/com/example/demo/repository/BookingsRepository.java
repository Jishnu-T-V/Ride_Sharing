package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.BookingsDTO;
import com.example.demo.entity.Bookings;

@Repository
public interface BookingsRepository extends JpaRepository<Bookings, Integer> {
	List<Bookings> findByRideSchedulesId(int rideScheduleId);
}