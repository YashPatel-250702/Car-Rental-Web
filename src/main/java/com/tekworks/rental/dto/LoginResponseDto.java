package com.tekworks.rental.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
	
	private String email;
	private String name;
	private String phoneNo;
	private String jwtToken;
	private Instant loginTIme;

}
