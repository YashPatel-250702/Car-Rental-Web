package com.tekworks.rental.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tekworks.rental.dto.BookingHistoryDto;
import com.tekworks.rental.dto.BookingStatusUpdate;
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

	@Transactional(rollbackForClassName  = "java.lang.Exception")
	public void bookCar(BookingHistoryDto bookingHistoryDto) {
		Users user = usersRepository.findById(bookingHistoryDto.getUserId())
				.orElseThrow(() -> new RuntimeException("User Not found with id: " + bookingHistoryDto.getUserId()));

		Cars car = carRepository.findById(bookingHistoryDto.getCarId())
				.orElseThrow(() -> new RuntimeException("Car not found with id: " + bookingHistoryDto.getCarId()));

		BookingHistory bookingHistory = convertToEntity(bookingHistoryDto);
		bookingHistory.setUser(user);
		bookingHistory.setCar(car);

		bookingHistory.setBookingDate(LocalDateTime.now());
		bookingHistoryRepository.save(bookingHistory);
	}

	public List<BookingHistoryDto> getUpcomingBooking(Long userId) {
		if (!usersRepository.existsById(userId)) {
			throw new RuntimeException("User not found with id:  " + userId);
		}

		LocalDateTime now = LocalDateTime.now();
		List<BookingHistory> bookings = bookingHistoryRepository.findByUserIdAndPickupDateAfterAndJourneyStatus(userId,
				now, "upcoming");

		if(bookings.isEmpty()) {
			throw new RuntimeException("No Umcoming bookings found");
		}
		System.out.println("Upcoming Bookings are: " + bookings);
		return bookings.stream().map(this::convertToDto).collect(Collectors.toList());
	}
	
	public List<BookingHistoryDto> getCompleteBooking(Long userId) {
		if (!usersRepository.existsById(userId)) {
			throw new RuntimeException("User not found with id:  " + userId);
		}

		List<BookingHistory> bookings = bookingHistoryRepository.findByUserIdAndJourneyStatus(userId,
				"completed");

		if(bookings.isEmpty()) {
			throw new RuntimeException("No Completed bookings found");
		}

		System.out.println("Completed Bookings are: " + bookings);
		return bookings.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	
	public List<BookingHistoryDto> getCancleBooking(Long userId) {
		if (!usersRepository.existsById(userId)) {
			throw new RuntimeException("User not found with id:  " + userId);
		}

		List<BookingHistory> bookings = bookingHistoryRepository.findByUserIdAndJourneyStatus(userId,
				"cancled");

		
		if(bookings.isEmpty()) {
			throw new RuntimeException("No cancle bookings found");
		}
		System.out.println("Cancled Bookings are: " + bookings);
		return bookings.stream().map(this::convertToDto).collect(Collectors.toList());
	}
	
	@Transactional(rollbackForClassName  = "java.lang.Exception")
	public BookingHistory updateBookingStatus(BookingStatusUpdate bookingStatusUpdate,Long userId) {
		
		if(!usersRepository.existsById(userId)) {
			throw new RuntimeException("User not found with id:  " + userId);
		}
		if(!carRepository.existsById(bookingStatusUpdate.getCarId())) {
			throw new RuntimeException("Car not found with id:  " + bookingStatusUpdate.getCarId());
		}
	
		BookingHistory booking = bookingHistoryRepository.findByUserIdAndCarIdAndJourneyStatus(userId, bookingStatusUpdate.getCarId(),"upcoming");
		
		if(booking==null) {
			throw new RuntimeException("Booking  not found with user id:  " + userId+" and car id: "+bookingStatusUpdate.getCarId());
		}
		
		booking.setJourneyStatus(bookingStatusUpdate.getNewStatus());
		return bookingHistoryRepository.save(booking);
	}
	
	
	
	// converting dto to entity
	public BookingHistory convertToEntity(BookingHistoryDto bookingHistoryDto) {
		BookingHistory bookingHistory = new BookingHistory();

		bookingHistory.setBookingCity(bookingHistoryDto.getBookingCity());
		bookingHistory.setPickupLocation(bookingHistoryDto.getPickupLocation());

		bookingHistory.setBookingDate(bookingHistoryDto.getBookingDate());
		bookingHistory.setPickupDate(bookingHistoryDto.getPickupDate());
		bookingHistory.setDropoffDate(bookingHistoryDto.getDropoffDate());
		bookingHistory.setTotalCost(bookingHistoryDto.getTotalCost());
		bookingHistory.setJourneyStatus(bookingHistoryDto.getJourneyStatus());
		return bookingHistory;
	}

	// converting booking entity to dto
	private BookingHistoryDto convertToDto(BookingHistory booking) {
		BookingHistoryDto dto = new BookingHistoryDto();
		dto.setId(booking.getId());
		dto.setBookingCity(booking.getBookingCity());
		dto.setPickupLocation(booking.getPickupLocation());

		dto.setBookingDate(booking.getBookingDate());
		dto.setPickupDate(booking.getPickupDate());
		dto.setDropoffDate(booking.getDropoffDate());
		dto.setTotalCost(booking.getTotalCost());
		dto.setJourneyStatus(booking.getJourneyStatus());
		dto.setUserId(booking.getUser().getId());
		dto.setCarId(booking.getCar().getId());
		return dto;
	}

}
