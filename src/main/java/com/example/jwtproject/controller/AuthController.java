package com.example.jwtproject.controller;

import com.example.jwtproject.dto.AuthResponse;
import com.example.jwtproject.dto.LoginRequest;
import com.example.jwtproject.dto.RegisterRequest;
import com.example.jwtproject.dto.UserProfileResponse;
import com.example.jwtproject.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication Management", description = "Endpoints for Registration, Login, and Profile Retrieval")
public class AuthController {

        private final AuthService authService;

        public AuthController(AuthService authService) {
                this.authService = authService;
        }

        @Operation(summary = "Register a new user", description = "Creates a new user account with encrypted BCrypt password and returns JWT token.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "User successfully registered", content = @Content(schema = @Schema(implementation = AuthResponse.class))),
                        @ApiResponse(responseCode = "400", description = "Validation error (invalid fields)"),
                        @ApiResponse(responseCode = "409", description = "Conflict (username or email already exists)")
        })
        @PostMapping("/register")
        public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
                AuthResponse response = authService.register(request);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        @Operation(summary = "Authenticate user and issue JWT", description = "Authenticates user credentials and returns a signed JWT access token.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Login successful", content = @Content(schema = @Schema(implementation = AuthResponse.class))),
                        @ApiResponse(responseCode = "400", description = "Invalid request format"),
                        @ApiResponse(responseCode = "401", description = "Invalid credentials")
        })
        @PostMapping("/login")
        public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
                AuthResponse response = authService.login(request);
                return ResponseEntity.ok(response);
        }

        @Operation(summary = "Get current user profile", description = "Retrieves details of the currently authenticated user using the JWT Bearer token.", security = @SecurityRequirement(name = "Bearer Authentication"))
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Profile retrieved successfully", content = @Content(schema = @Schema(implementation = UserProfileResponse.class))),
                        @ApiResponse(responseCode = "401", description = "Unauthorized - Missing, invalid, or expired JWT token")
        })
        @GetMapping("/profile")
        public ResponseEntity<UserProfileResponse> getProfile(Authentication authentication) {
                String username = authentication.getName();
                UserProfileResponse profile = authService.getProfile(username);
                return ResponseEntity.ok(profile);
        }
}
