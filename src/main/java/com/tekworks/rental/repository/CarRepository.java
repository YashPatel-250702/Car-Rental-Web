package com.tekworks.rental.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tekworks.rental.entity.Cars;

public interface CarRepository extends JpaRepository<Cars, Long>{

}
