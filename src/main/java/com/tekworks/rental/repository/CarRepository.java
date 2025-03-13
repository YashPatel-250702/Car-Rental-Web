package com.tekworks.rental.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tekworks.rental.entity.Cars;

public interface CarRepository extends JpaRepository<Cars, Long>{

	 Optional<Cars> findByRegistrationNumber(String registrationNumber);
}
