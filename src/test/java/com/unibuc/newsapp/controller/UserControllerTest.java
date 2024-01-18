package com.unibuc.newsapp.controller;

import com.unibuc.newsapp.dto.AuthResponse;
import com.unibuc.newsapp.dto.LoginRequest;
import com.unibuc.newsapp.dto.RegisterDTO;
import com.unibuc.newsapp.entity.Role;
import com.unibuc.newsapp.entity.User;
import com.unibuc.newsapp.repository.UserRepository;
import com.unibuc.newsapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;


    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() {
        RegisterDTO registerDTO = new RegisterDTO("username", "password", "email@example.com");
        when(userService.addUser(any(User.class))).thenReturn(new User());

        ResponseEntity<?> response = userController.registerUser(registerDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testLoginUser() {
        // Setup
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        User mockUser = new User();
        mockUser.setUsername(loginRequest.getUsername());
        Role mockRole = new Role();
        mockRole.setName("USER");
        mockUser.setRole(mockRole);

        // Mock the behavior
        when(userService.authenticateUser(anyString(), anyString())).thenReturn(mockUser);

        // Perform the test
        ResponseEntity<?> response = userController.loginUser(loginRequest);

        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //the response message should be "User authenticated" and the token should not be null
        assertEquals("User authenticated", ((AuthResponse) response.getBody()).getMessage());
        assertNotNull(((AuthResponse) response.getBody()).getJwt());
    }

}
