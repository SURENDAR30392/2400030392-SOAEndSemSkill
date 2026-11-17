package com.example.jwtproject.controller;

import com.example.jwtproject.dto.ApiResponse;
import com.example.jwtproject.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "Role-Based Protected Endpoints", description = "Endpoints demonstrating Role-Based Access Control (RBAC)")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Operation(
            summary = "User Dashboard",
            description = "Protected endpoint accessible to both USER and ADMIN roles."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Access granted"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized - Missing or invalid token"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions")
    })
    @GetMapping("/user/dashboard")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getUserDashboard(Authentication authentication) {
        Map<String, Object> data = new HashMap<>();
        data.put("username", authentication.getName());
        data.put("authorities", authentication.getAuthorities());
        data.put("dashboardType", "Standard User Dashboard");
        data.put("info", "Welcome to the User Dashboard! You have standard user privileges.");

        return ResponseEntity.ok(ApiResponse.success("Access granted to User Dashboard", data));
    }

    @Operation(
            summary = "Admin Dashboard",
            description = "High-privilege protected endpoint accessible exclusively to users with ADMIN role."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Access granted to Admin"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized - Missing or invalid token"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden - ADMIN role required")
    })
    @GetMapping("/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getAdminDashboard(Authentication authentication) {
        Map<String, Object> data = new HashMap<>();
        data.put("adminUsername", authentication.getName());
        data.put("authorities", authentication.getAuthorities());
        data.put("dashboardType", "Administrative Command Center");
        data.put("totalRegisteredUsers", userRepository.count());
        data.put("adminPrivileges", listOfPrivileges());
        data.put("systemStatus", "OPERATIONAL");

        return ResponseEntity.ok(ApiResponse.success("Access granted to Administrator Dashboard", data));
    }

    private Object listOfPrivileges() {
        return new String[]{"USER_MANAGEMENT", "SYSTEM_METRICS", "TOKEN_AUDITING", "DATABASE_INSPECTION"};
    }
}
