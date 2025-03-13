package com.tekworks.rental.controller;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tekworks.rental.dto.CarDTO;
import com.tekworks.rental.response.ErrorResponse;
import com.tekworks.rental.response.SuccessResponse;
import com.tekworks.rental.service.CarService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/car")
public class CarController {

	@Autowired
	private CarService carService;
	
	@PostMapping("/saveCar")
	public ResponseEntity<?> saveCar(@Valid @RequestBody CarDTO carDTO) {
		try {
			carService.saveCarInfo(carDTO);
			return ResponseEntity.status(HttpStatus.OK)
					.body("CarInfo Successfully saved");
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), Instant.now()));
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", Instant.now()));
		}
	}
	
	
	@GetMapping("/getAllCars")
	public ResponseEntity<?> getAllCars(){
		try {
			List<CarDTO> allCars = carService.getAllCars();
			if(allCars.isEmpty()||allCars==null) {
				return ResponseEntity.status(HttpStatus.OK)
						.body("No Car Found");
			}
			return ResponseEntity.status(HttpStatus.OK)
					.body(new SuccessResponse(HttpStatus.OK, "All Available Cars", allCars));
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", Instant.now()));
		}
	}
	

}
