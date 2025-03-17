package com.tekworks.rental.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bookingCity;
    private String pickupLocation;
    private String dropoffLocation;
    private LocalDateTime bookingDate;
    private LocalDateTime pickupDate;
    private LocalDateTime dropoffDate;
    private Double totalCost;
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user; 

    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Cars car;
}
