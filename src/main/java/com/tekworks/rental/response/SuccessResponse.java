package com.tekworks.rental.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResponse {
	
	private HttpStatus status;
	private String message;
	private Object data;
	
	
}
 
