package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.RideHistory;

@Repository
public interface RideHistoryRepository extends JpaRepository<RideHistory, Integer> {
	public boolean existsByUserId(int userId);

	public String deleteByUserId(int userId);

	List<RideHistory> findByUserIdAndRideDateBetween(int userId, LocalDate startDate, LocalDate endDate);
	
	List<RideHistory> findByUserId(int userId);
}
