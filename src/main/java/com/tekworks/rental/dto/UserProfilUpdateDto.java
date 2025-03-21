package com.tekworks.rental.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfilUpdateDto {
	
	@NotBlank(message = "PhoneNo is reuired")
	private String phoneNo;
	@NotBlank(message = "Name is  required")
	private String name;
	@NotBlank(message = "Email is required")
	private String email;
	
	@NotBlank(message = "City is required")
	private String city;
	
	@NotBlank(message = "LicenseNo is Required")
	private String licenseNo;
	
}
