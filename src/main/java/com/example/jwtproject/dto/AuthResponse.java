package com.example.jwtproject.dto;

import com.example.jwtproject.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Authentication response containing JWT access token")
public class AuthResponse {

    @Schema(description = "JWT Bearer Access Token", example = "eyJhbGciOiJIUzI1NiJ9...")
    private String token;

    @Schema(description = "Token Type", example = "Bearer")
    private String tokenType = "Bearer";

    @Schema(description = "Token expiration duration in milliseconds", example = "86400000")
    private Long expiresIn;

    @Schema(description = "Username of the authenticated user", example = "john_doe")
    private String username;

    @Schema(description = "Email of the authenticated user", example = "john@example.com")
    private String email;

    @Schema(description = "Assigned user role", example = "USER")
    private Role role;

    @Schema(description = "Status or success message", example = "User authenticated successfully")
    private String message;

    public AuthResponse() {
    }

    public AuthResponse(String token, Long expiresIn, String username, String email, Role role, String message) {
        this.token = token;
        this.tokenType = "Bearer";
        this.expiresIn = expiresIn;
        this.username = username;
        this.email = email;
        this.role = role;
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
