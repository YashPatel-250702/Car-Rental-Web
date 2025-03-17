package com.tekworks.rental.controller;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tekworks.rental.dto.BookingHistoryDto;
import com.tekworks.rental.response.ErrorResponse;
import com.tekworks.rental.service.BookingHistoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/booking")
public class BookingHistoryController {

	@Autowired
	private BookingHistoryService bookingHistoryService;

	@PostMapping("/bookCar/{userId}/{carId}")
	public ResponseEntity<?> bookCar(@Valid @RequestBody BookingHistoryDto bookingHistoryDto, @PathVariable Long userId,
			@PathVariable Long carId) {

		try {

			bookingHistoryDto.setUserId(userId);
			bookingHistoryDto.setCarId(carId);
			bookingHistoryService.bookCar(bookingHistoryDto);
			return ResponseEntity.ok("Car booked Successfully");

		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage(), Instant.now()));
		}

		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", Instant.now()));
		}

	}
}
