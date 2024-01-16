package com.unibuc.newsapp.controller;

import com.unibuc.newsapp.dto.AuthResponse;
import com.unibuc.newsapp.dto.LoginRequest;
import com.unibuc.newsapp.entity.Role;
import com.unibuc.newsapp.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.unibuc.newsapp.entity.User;
import com.unibuc.newsapp.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User registeredUser = userService.addUser(user);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        User authenticatedUser = userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
        //get user role by role id
        Role role = authenticatedUser.getRole();

        if (authenticatedUser != null) {
            String token = JwtUtil.generateToken(authenticatedUser.getUsername(), role.getName());
            return ResponseEntity.ok(new AuthResponse("User authenticated", token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse("Authentication failed"));
        }
    }

    @GetMapping("/current-user")
    public ResponseEntity<?> getCurrentUser(@RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7);

            try {
                JwtUtil.validateToken(jwt);
            } catch (Exception e) {
                return ResponseEntity.ok("Not logged in");
            }
            String username = JwtUtil.extractUsername(jwt);


            return ResponseEntity.ok("Logged in as " + username);
        } else {
            return ResponseEntity.ok("Not logged in");
        }
    }

}
