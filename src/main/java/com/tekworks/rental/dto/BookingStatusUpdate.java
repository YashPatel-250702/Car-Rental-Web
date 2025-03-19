package com.tekworks.rental.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingStatusUpdate {
	
	
	@NotBlank(message = "Please provide new Status")
	private String newStatus;
	@NotNull(message = "Please provide car id")
	private Long carId;
	

}
