package com.tekworks.rental.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tekworks.rental.dto.CarDTO;
import com.tekworks.rental.entity.Cars;
import com.tekworks.rental.entity.Users;
import com.tekworks.rental.repository.CarRepository;
import com.tekworks.rental.repository.UsersRepository;

@Service
@Transactional
public class CarService {

	@Autowired
	private CarRepository carRepository;

	@Autowired

	private UsersRepository usersRepository;
    
	@Autowired
	private UserLoginService userLoginService;
  
	public void saveCarInfo(CarDTO carDTO) {
		Optional<Cars> byRegistrationNumber = carRepository.findByRegistrationNumber(carDTO.getRegistrationNumber());

		if (byRegistrationNumber.isPresent()) {
			throw new RuntimeException("Car With registration number already presnet");
		}

		Cars car = convertToEntity(carDTO);
		carRepository.save(car);
	}

	// converting car dto to car entity
	public Cars convertToEntity(CarDTO carDTO) {

		Cars car = new Cars();
		car.setCarName(carDTO.getCarName());
		car.setCarModel(carDTO.getCarModel());
		car.setPerDayCharge(carDTO.getPerDayCharge());
		car.setAvaibleCities(carDTO.getAvailableCities());
		car.setOwnerName(carDTO.getOwnerName());
		car.setCarImageUrl(carDTO.getCarImageUrl());
		car.setRegistrationNumber(carDTO.getRegistrationNumber());

		return car;
	}

	// getting all cars
	public List<CarDTO> getAllCars() {
		List<Cars> allCars = carRepository.findAll();
		if (allCars.isEmpty()) {
			return null;
		}

		return convertToDto(allCars);
	}

	public List<CarDTO> convertToDto(List<Cars> cars) {

		return cars.stream().map(car -> {
			CarDTO carDTO = new CarDTO();
			carDTO.setId(car.getId());
			carDTO.setRegistrationNumber(car.getRegistrationNumber());
			carDTO.setAvailableCities(car.getAvaibleCities());
			carDTO.setCarName(car.getCarName());
			carDTO.setPerDayCharge(car.getPerDayCharge());
			carDTO.setCarModel(car.getCarModel());
			carDTO.setOwnerMobileNumber(car.getOwnerMobileNumber());
			carDTO.setCarImageUrl(car.getCarImageUrl());
			carDTO.setOwnerName(car.getOwnerName());
			return carDTO;
		}).collect(Collectors.toList());
	}

	public List<CarDTO> getCarsByUserCity(Long userId) {
		Users user = userLoginService.getUserById(userId);

		String city = user.getCity();
		List<Cars> byCity = carRepository.findByCity(city);
			
		return byCity.isEmpty()?null:convertToDto(byCity);
		
	}


    public Cars getCarById(Long carId) {
        return carRepository.findById(carId).orElse(null);
    }




}


