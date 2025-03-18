package com.tekworks.rental.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tekworks.rental.entity.BookingHistory;

public interface BookingHistoryRepository extends JpaRepository<BookingHistory, Long> {
	@Query("SELECT b FROM BookingHistory b WHERE b.user.id = :userId AND b.pickupDate > :currentDateTime AND LOWER(b.journeyStatus) = LOWER(:journeyStatus)")
	List<BookingHistory> findByUserIdAndPickupDateAfterAndJourneyStatus(Long userId, LocalDateTime currentDateTime,
			String journeyStatus);
}
