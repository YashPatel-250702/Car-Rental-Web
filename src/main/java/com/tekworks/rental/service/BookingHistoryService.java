package com.tekworks.rental.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tekworks.rental.dto.BookingHistoryDto;
import com.tekworks.rental.entity.BookingHistory;
import com.tekworks.rental.entity.Cars;
import com.tekworks.rental.entity.Users;
import com.tekworks.rental.repository.BookingHistoryRepository;
import com.tekworks.rental.repository.CarRepository;
import com.tekworks.rental.repository.UsersRepository;

@Service
public class BookingHistoryService {

	
	@Autowired
	private BookingHistoryRepository bookingHistoryRepository;
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private CarRepository carRepository;
	
	public void bookCar(BookingHistoryDto bookingHistoryDto) {
		Users user = usersRepository.findById(bookingHistoryDto.getUserId())
		        .orElseThrow(() -> new RuntimeException("User Not found with id: " + bookingHistoryDto.getUserId()));

		
		Cars car = carRepository.findById(bookingHistoryDto.getCarId())
		        .orElseThrow(()->new RuntimeException("Car not found with id: "+bookingHistoryDto.getCarId()));
		
		BookingHistory bookingHistory = convertToEntity(bookingHistoryDto);
		bookingHistory.setUser(user);
		bookingHistory.setCar(car);
		
		bookingHistoryRepository.save(bookingHistory);
	}
	
	public BookingHistory convertToEntity(BookingHistoryDto bookingHistoryDto) {
	    BookingHistory bookingHistory = new BookingHistory();
	    
	    bookingHistory.setBookingCity(bookingHistoryDto.getBookingCity());
	    bookingHistory.setPickupLocation(bookingHistoryDto.getPickupLocation());
	    bookingHistory.setDropoffLocation(bookingHistoryDto.getDropoffLocation());
	    bookingHistory.setBookingDate(bookingHistoryDto.getBookingDate());
	    bookingHistory.setPickupDate(bookingHistoryDto.getPickupDate());
	    bookingHistory.setDropoffDate(bookingHistoryDto.getDropoffDate());
	    bookingHistory.setTotalCost(bookingHistoryDto.getTotalCost());
	    bookingHistory.setStatus(bookingHistoryDto.getStatus());
	    return bookingHistory;
	}

}
