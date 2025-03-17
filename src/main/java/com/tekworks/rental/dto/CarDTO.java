package com.tekworks.rental.dto;
 
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDTO {
	
    @NotBlank(message = "Car Name is required")
    private String carName;
    @NotBlank(message = "Car Model is required")
    private String carModel;
    
    @NotBlank(message = "Car ImageUrl is required")
    private String carImageUrl;
    @NotNull(message = "PerDay charge is required")
    private Double perDayCharge;
    
    @NotEmpty(message = "List of cities is required")
    private List<String> availableCities;
    
    @NotBlank(message = "Registration number is required")
    private String registrationNumber;
    
    @NotBlank(message = "Car Owner name is required")
    private String ownerName;
    
    @NotBlank(message = "Car Owner Mobile Number is required")
    private String ownerMobileNumber;
}


