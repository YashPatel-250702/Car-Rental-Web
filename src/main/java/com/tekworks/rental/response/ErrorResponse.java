package com.tekworks.rental.response;

import java.time.Instant;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

	private HttpStatus status;
	private Object message;
	private Instant time ;

}
