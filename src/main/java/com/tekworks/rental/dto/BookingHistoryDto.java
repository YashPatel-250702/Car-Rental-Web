package com.tekworks.rental.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingHistoryDto {

    private Long id;

    @NotBlank(message = "Booking city cannot be empty")
    private String bookingCity;

    @NotBlank(message = "Pickup location cannot be empty")
    private String pickupLocation;

    private LocalDateTime bookingDate;

    @NotNull(message = "Pickup date cannot be null")
    @Future(message = "Pickup date must be in the future")
    private LocalDateTime pickupDate;

    @NotNull(message = "Dropoff date cannot be null")
    @Future(message = "Dropoff date must be in the future")
    private LocalDateTime dropoffDate;

    @NotNull(message = "Total cost cannot be null")
    @Positive( message = "Total cost must be a positive number")
    private Double totalCost;

    @NotBlank(message = "Status cannot be empty")
    private String journeyStatus;

    private Long userId;
    private Long carId;
}
