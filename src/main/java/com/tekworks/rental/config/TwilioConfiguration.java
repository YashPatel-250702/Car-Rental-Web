package com.tekworks.rental.config;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class TwilioConfiguration {

    private String twilioAccountSid=System.getenv("TWILIO_ACCOUNT_SID");
    private String twilioAuthToken=System.getenv("TWILIO_AUTH_TOKEN");
    private String phoneNumber=System.getenv("TWILIO_PHONE_NUMBER");
    
    
}
