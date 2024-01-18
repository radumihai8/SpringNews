package com.unibuc.newsapp.service;

import com.unibuc.newsapp.entity.Role;
import com.unibuc.newsapp.entity.User;
import com.unibuc.newsapp.exceptions.ResourceNotFoundException;
import com.unibuc.newsapp.repository.RoleRepository;
import com.unibuc.newsapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addUserTest() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("test@unibuc.ro");

        Role role = new Role("USER");
        when(roleRepository.findByName("USER")).thenReturn(Optional.of(role));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.addUser(user);

        assertNotNull(savedUser);
        assertEquals("username", savedUser.getUsername());
        assertEquals(role, savedUser.getRole());
    }

    @Test
    public void authenticateUserTest() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("test@unibuc.ro");

        when(userRepository.findByUsername("username")).thenReturn(user);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        User authenticatedUser = userService.authenticateUser("username", "password");

        assertNotNull(authenticatedUser);
        assertEquals("username", authenticatedUser.getUsername());
    }

    @Test
    public void authenticateUserFailedTest() {
        when(userRepository.findByUsername("username")).thenReturn(null);

        User authenticatedUser = userService.authenticateUser("username", "password");

        assertNull(authenticatedUser);
    }
}
