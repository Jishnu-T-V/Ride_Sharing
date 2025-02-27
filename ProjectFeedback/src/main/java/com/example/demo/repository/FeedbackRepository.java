package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
	List<Feedback> findByMotoristId(int motoristId);

	List<Feedback> findByRiderId(int riderId);
}
