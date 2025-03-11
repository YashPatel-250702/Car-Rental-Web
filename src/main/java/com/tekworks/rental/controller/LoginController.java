package com.tekworks.rental.controller;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tekworks.rental.dto.LoginDto;
import com.tekworks.rental.dto.LoginResponseDto;
import com.tekworks.rental.dto.UserDto;
import com.tekworks.rental.entity.Users;
import com.tekworks.rental.repository.UsersRepository;
import com.tekworks.rental.response.ErrorResponse;
import com.tekworks.rental.response.SuccessResponse;
import com.tekworks.rental.service.UserLoginService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class LoginController {

	@Autowired
	private UserLoginService userLoginService;

	@Autowired
	private UsersRepository repository;

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody Users users, BindingResult bindingResult) {
	   
		try {
			 if (bindingResult.hasErrors()) {
			        Map<String, String> errors = new HashMap<>();
			        bindingResult.getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

			        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			                .body( errors);
			    }

			    if (repository.existsByEmail(users.getEmail())) {
			        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			                .body(new ErrorResponse(HttpStatus.BAD_REQUEST, "Email Already Registered", Instant.now()));
			    }

			    if (repository.existsByPhoneNo(users.getPhoneNo())) {
			        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			                .body(new ErrorResponse(HttpStatus.BAD_REQUEST, "Phone Number Already Registered", Instant.now()));
			    }

			    userLoginService.register(users);
			    return ResponseEntity.status(HttpStatus.OK)
			            .body(new SuccessResponse(HttpStatus.OK, "Registered Successfully", null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", Instant.now()));
		}
	}


	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDto dto) {

		try {
			LoginResponseDto login = userLoginService.login(dto);

			if (login == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ErrorResponse(HttpStatus.NOT_FOUND, "No user found with credentials", Instant.now()));
			}

			return ResponseEntity.status(HttpStatus.OK)
					.body(new SuccessResponse(HttpStatus.OK, "Login Successfully", login));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", Instant.now()));
		}
	}
	
	@GetMapping("/viewProfile/{email}")
	public ResponseEntity<?> getUserDetails(@PathVariable String email){
		try {
			
			UserDto user = userLoginService.getUserByEmail(email);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new SuccessResponse(HttpStatus.OK, "User Detail", user));
		}catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage(), Instant.now()));
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", Instant.now()));
		}
	
	}

}
