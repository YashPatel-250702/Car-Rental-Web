package com.tekworks.rental.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tekworks.rental.entity.Users;
import java.util.List;


public interface UsersRepository extends JpaRepository<Users, Long>{
	Users findByEmail(String email);
	
	 boolean existsByEmail(String email);
	 
	 boolean existsByPhoneNo(String phoneNo);
}
