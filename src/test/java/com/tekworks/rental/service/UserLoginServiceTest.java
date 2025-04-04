package com.tekworks.rental.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tekworks.rental.config.TwilioConfiguration;
import com.tekworks.rental.dto.UserDto;
import com.tekworks.rental.entity.Users;
import com.tekworks.rental.repository.UsersRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

@ExtendWith(MockitoExtension.class)
public class UserLoginServiceTest {

    @Mock
    private UsersRepository repository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private TwilioConfiguration twilioConfiguration;

    @InjectMocks
    private UserLoginService userLoginService;

    private final String testPhoneNumber = "+1234567890";

    @BeforeEach
    void before() {
        lenient().when(twilioConfiguration.getPhoneNumber()).thenReturn("+11234567890");
    }

    @Test
    void RegisterUserTest() {
        UserDto dto = new UserDto();
        dto.setName("Yash Patel");
        dto.setCity("Vijayawada");
        dto.setEmail("patelyash@gmail.com");
        dto.setPassword("Yash@123");
        dto.setPhoneNo("9301498676");
        dto.setRole("ROLE_USER");
        dto.setLicenseNo("1234YAsh");

        Users user = convertToEntity(dto);
        user.setCreatedAt(Instant.now());

        when(repository.save(any(Users.class))).thenReturn(user);

        Users savedUser = userLoginService.register(dto);

        assertAll(
            () -> assertEquals("Yash Patel", savedUser.getName()),
            () -> assertEquals("Vijayawada", savedUser.getCity()),
            () -> assertEquals("patelyash@gmail.com", savedUser.getEmail()),
            () -> assertEquals("9301498676", savedUser.getPhoneNo()),
            () -> assertEquals("ROLE_USER", savedUser.getRole()),
            () -> assertEquals("1234YAsh", savedUser.getLicenseNo())
        );

       
    }

    private Users convertToEntity(UserDto userDto) {
        Users user = new Users();
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setPhoneNo(userDto.getPhoneNo());
        user.setLicenseNo(userDto.getLicenseNo());
        user.setCity(userDto.getCity());
        user.setRole(userDto.getRole());
        return user;
    }

    @Test
    void sendOtpToPhoneTest() {
        Message mockMessage = mock(Message.class);
        lenient().when(mockMessage.getSid()).thenReturn("MockSID123");

        try (MockedStatic<Message> mockedMessage = mockStatic(Message.class)) {
            mockedMessage.when(() -> Message.creator(
                    any(PhoneNumber.class),
                    any(PhoneNumber.class),
                    anyString()
            )).thenAnswer(invocation -> {
                MessageCreator mockMessageCreator = mock(MessageCreator.class);
                when(mockMessageCreator.create()).thenReturn(mockMessage);
                return mockMessageCreator;
            });

            String response = userLoginService.sendOtpToPhone(testPhoneNumber);
            assertEquals("Otp Send Successfully", response);

            mockedMessage.verify(() -> Message.creator(
                    any(PhoneNumber.class),
                    any(PhoneNumber.class),
                    anyString()
            ));
        }
    }

    @Test
    void sendOtpToPhoneTestFailedTest() {
        try (MockedStatic<Message> mockedMessage = mockStatic(Message.class)) {
            mockedMessage.when(() -> Message.creator(
                    any(PhoneNumber.class),
                    any(PhoneNumber.class),
                    anyString()
            ).create()).thenThrow(new RuntimeException("Error"));

            String response = userLoginService.sendOtpToPhone(testPhoneNumber);
            assertEquals("Error while sending Otp", response);
        }
    }

    @Test
    void getUserByEmailSuccessTest() {
        Users mockUser = new Users();
        mockUser.setId(1L);
        mockUser.setEmail("Yash@gmail.com");
        mockUser.setName("Yash");

        when(repository.findByEmail(anyString())).thenReturn(mockUser);

        UserDto result = userLoginService.getUserByEmail("Yash@gmail.com");

        assertNotNull(result);
        assertEquals("Yash@gmail.com", result.getEmail());
        assertEquals("Yash", result.getName());

  
    }

    @Test
    void getUserByEmailFailedTest() {
        when(repository.findByEmail(anyString())).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userLoginService.getUserByEmail("notfound@example.com");
        });

        assertEquals("User Not found with email", exception.getMessage());
    }

    @Test
    void getUserByIdTest() {
        Users mockUser = new Users();
        mockUser.setId(1L);
        mockUser.setEmail("Yash@gmail.com");

        when(repository.findById(anyLong())).thenReturn(Optional.of(mockUser));

        UserDto result = userLoginService.getUserById(1L);

        assertNotNull(result);
        assertEquals("Yash@gmail.com", result.getEmail());

       ;
    }

    @Test
    void getUserByIdFailedTest() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userLoginService.getUserById(100L);
        });

        assertEquals("User not found with id: 100", exception.getMessage());
        verify(repository, times(1)).findById(100L);
    }

    
}
