package com.tekworks.rental.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	@NotBlank(message = "PhoneNo is reuired")
	private String phoneNo;
	@NotBlank(message = "Name is  required")
	private String name;
	@NotBlank(message = "Email is required")
	private String email;
	@NotBlank(message = "Password Is required")
	private String password;
	@NotBlank(message = "City is required")
	private String City;
	
	@NotBlank(message = "LicenseNo is Required")
	private String licenseNo;
	
	private String role;
	
	
	
}
