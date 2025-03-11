package com.tekworks.rental.entity;

import java.time.Instant;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "PhoneNo is reuired")
	private String phoneNo;
	@NotNull(message = "Name is  required")
	private String name;
	@NotNull(message = "Email is required")
	private String email;
	@NotNull(message = "Password Is required")
	private String password;
	@NotNull(message = "Address is required")
	private String Address;
	
	
	private Instant createdAt;
	private Instant updatedAt;
	
}
