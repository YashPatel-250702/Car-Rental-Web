package com.tekworks.rental.service;

import java.time.Instant;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tekworks.rental.dto.LoginDto;
import com.tekworks.rental.dto.LoginResponseDto;
import com.tekworks.rental.dto.UserDto;
import com.tekworks.rental.entity.Users;
import com.tekworks.rental.repository.UsersRepository;

@Service
public class UserLoginService {
	
	@Autowired 
	private UsersRepository usersRepository;

	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public LoginResponseDto login(LoginDto dto) {
	    Users user = usersRepository.findByEmail(dto.getEmail());

	    if (user == null) {
	        System.out.println("User not found");
	        return null;
	    }


	    if (encoder.matches(dto.getPassword(), user.getPassword())) {
	        LoginResponseDto responseDto = new LoginResponseDto();

	        String token = jwtService.generateToken(user.getEmail());

	        responseDto.setEmail(dto.getEmail());
	        responseDto.setName(user.getName());
	        responseDto.setJwtToken(token);
	        responseDto.setLoginTIme(Instant.now());
	        responseDto.setPhoneNo(user.getPhoneNo());

	        return responseDto;
	    }

	    return null;
	}

	
	public void register(Users user) {
		String encode = encoder.encode(user.getPassword());
		user.setPassword(encode);
		user.setCreatedAt(Instant.now());
		usersRepository.save(user);
	}
	
	public UserDto getUserByEmail(String email) {
		Users user = usersRepository.findByEmail(email);
		if(user==null) {
			throw new RuntimeException("User Not found with email : "+email);
		}
		return convertToDTO(user);
	}
	
	private UserDto convertToDTO(Users user) {
		UserDto dto=new UserDto();
		dto.setEmail(user.getEmail());
		dto.setPhoneNo(user.getPhoneNo());
		dto.setName(user.getName());
		dto.setAddress(user.getAddress());
		return dto;
	
	}

}
