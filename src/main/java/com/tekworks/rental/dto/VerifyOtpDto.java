package com.tekworks.rental.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyOtpDto {
	@NotBlank(message = "Mobile number required for OTP")
	@Pattern(regexp = "^\\+91[6-9]\\d{9}$", message = "Invalid mobile number. Must start with +91 and 6,7,8,9 and be 10 digits long.")
	private String mobileNumber;
	
	@NotBlank(message = "Otp is required")
	private String otp;

}
