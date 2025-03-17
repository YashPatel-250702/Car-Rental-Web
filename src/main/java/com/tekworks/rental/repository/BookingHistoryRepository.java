package com.tekworks.rental.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tekworks.rental.entity.BookingHistory;

public interface BookingHistoryRepository extends JpaRepository<BookingHistory, Long>{

}
