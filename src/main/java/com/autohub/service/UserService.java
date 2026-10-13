package com.autohub.service;

import com.autohub.dto.AuthRequest;
import com.autohub.dto.AuthResponse;
import com.autohub.dto.ChangePasswordRequest;
import com.autohub.dto.RegisterRequest;
import com.autohub.model.Customer;
import com.autohub.model.User;
import com.autohub.repository.CustomerRepository;
import com.autohub.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already registered: " + req.getEmail());
        }

        String role = (req.getRole() != null && req.getRole().equalsIgnoreCase("ADMIN"))
                ? "ROLE_ADMIN" : "ROLE_CUSTOMER";
        String userRole = role.equals("ROLE_ADMIN") ? "ADMIN" : "CUSTOMER";

        String encodedPassword = passwordEncoder.encode(req.getPassword());
        User user = new User(req.getName(), req.getEmail(), encodedPassword, role, userRole, req.getPhone());
        User savedUser = userRepository.save(user);

        // Also automatically create customer record if customer role
        if ("CUSTOMER".equals(userRole)) {
            Customer customer = new Customer(req.getName(), req.getEmail(), req.getPhone() != null ? req.getPhone() : "", "Hyderabad");
            customerRepository.save(customer);
        }

        String token = "autohub-token-" + UUID.randomUUID();
        return new AuthResponse(token, savedUser.getId(), savedUser.getName(), savedUser.getEmail(), savedUser.getRole(), savedUser.getUserRole(), savedUser.getPhone(), "Registration successful");
    }

    public AuthResponse login(AuthRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        String token = "autohub-token-" + UUID.randomUUID();
        return new AuthResponse(token, user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getUserRole(), user.getPhone(), "Login successful");
    }

    public boolean changePassword(ChangePasswordRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + req.getEmail()));

        if (!passwordEncoder.matches(req.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Incorrect current password");
        }

        user.setPassword(passwordEncoder.encode(req.getNewPassword()));
        userRepository.save(user);
        return true;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
