package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Distances;

@Repository
public interface DistancesRepository extends JpaRepository<Distances, Integer> {
	Optional<Distances> findByFromAndTo(String from, String to);
}
