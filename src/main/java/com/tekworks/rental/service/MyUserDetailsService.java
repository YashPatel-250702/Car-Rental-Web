package com.tekworks.rental.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tekworks.rental.entity.Users;
import com.tekworks.rental.repository.UsersRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	private UsersRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Users byEmail = repository.findByEmail(username);
		if(byEmail!=null) {
			return new User(byEmail.getEmail(), byEmail.getPassword(),
					Collections.singleton(new SimpleGrantedAuthority(byEmail.getRole())));
		}
		
		throw new UsernameNotFoundException("User not found with email: " + username);
	}
	 
}
