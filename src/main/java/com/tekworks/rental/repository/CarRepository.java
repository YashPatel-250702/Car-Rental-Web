package com.tekworks.rental.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tekworks.rental.entity.Cars;

public interface CarRepository extends JpaRepository<Cars, Long>{

	 Optional<Cars> findByRegistrationNumber(String registrationNumber);
	 
	 @Query(value = "SELECT * FROM cars WHERE :city = ANY(avaible_cities)", nativeQuery = true)
	    List<Cars> findByCity(@Param("city") String city);
}
