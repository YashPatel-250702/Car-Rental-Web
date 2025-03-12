package com.tekworks.rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.tekworks.rental.config.TwilioConfiguration;
import com.twilio.Twilio;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableAspectJAutoProxy
public class CarrentalApplication {
	
	@Autowired
	private TwilioConfiguration twilioConfiguration;
	
	
	@PostConstruct
    public void initTwilio() {
        Twilio.init(twilioConfiguration.getTwilioAccountSid(), twilioConfiguration.getTwilioAuthToken());
    }
	
	
	public static void main(String[] args) {
		SpringApplication.run(CarrentalApplication.class, args);
		
	}
	

}
