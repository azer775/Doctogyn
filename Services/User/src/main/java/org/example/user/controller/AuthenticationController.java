package org.example.user.controller;

import io.jsonwebtoken.Claims;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.example.user.model.dtos.AuthenticationRequest;
import org.example.user.model.dtos.AuthenticationResponse;
import org.example.user.model.dtos.Crew;
import org.example.user.model.dtos.RegistrationRequest;
import org.example.user.model.entities.Doctor;
import org.example.user.repository.DoctorRepository;
import org.example.user.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")  // Base path for auth endpoints (routed via gateway as /api/auth/**)
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;  // Service for auth logic

    @Autowired
    private DoctorRepository userRepository;  // Repo for user queries

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegistrationRequest request) throws MessagingException {
        // Register user and send activation email
        authenticationService.register(request);
        return ResponseEntity.accepted().build();  // 202 Accepted
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        System.out.println("Authentication attempt for email: " + request.getPwd());
        // Check if user exists
        Doctor user = userRepository.findByEmail(request.getEmail());
        /*if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AuthenticationResponse("Email not found"));
        }*/
        // Check if account is enabled
        /*if (!user.isEnabled()) {
            return ResponseEntity.status(HttpStatus.LOCKED).body(new AuthenticationResponse("Account not activated"));
        }*/
        // Authenticate and return JWT
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/activate-account")
    public boolean activateAccount(@RequestParam String token) throws MessagingException {
        // Activate account with token
        return authenticationService.activateAccount(token);
    }

    @GetMapping("/current")
    public int getCurrentUser(@RequestParam String token) {
        // Get user from JWT token (for testing)
        return authenticationService.getCurrentUser(token).getId();
    }

    @GetMapping("/userc")
    public Doctor getConnectedUser(@RequestHeader(value = "Authorization", required = false) String auth) {
        // Extract token from header and get user
        String jwtToken = auth.replace("Bearer ", "");
        return authenticationService.getCurrentUser(jwtToken);
    }
    @PostMapping("/addcrew")
    public void addCrew(@RequestBody Crew request, @RequestHeader(value = "Authorization", required = false) String auth) throws MessagingException {
        // Register crew member (no email sent)
        String jwtToken = auth.replace("Bearer ", "");
        Doctor admin = authenticationService.getCurrentUser(jwtToken);
        authenticationService.registerCrew(request,admin.getCabinet());
    }
    @PostMapping("getDoctorsByCabinet")
    public List<Integer> getDoctorsByCabinet(@RequestHeader(value = "Authorization", required = false) String auth) {
        String jwtToken = auth.replace("Bearer ", "");
        // Get doctors by cabinet ID
        return authenticationService.findByCabinetId(jwtToken);
    }

    @GetMapping("/all")
    public List<Doctor> getAllUsers() {
        // Get all users (protect in production)
        return userRepository.findAll();
    }

    @GetMapping("/role")
    public Map<String, String> getRole(@RequestHeader(value = "Authorization", required = false) String auth) {
        // Get role from JWT
        Map<String, String> response = new HashMap<>();
        response.put("role", authenticationService.getRole(auth));
        return response;
    }

    @PostMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestHeader("Authorization") String authHeader) {
        // New endpoint for token validation (for other microservices)
        String jwt = authHeader.replace("Bearer ", "");
        try {
            // Extract and validate claims
            Claims claims = authenticationService.jwtService.extractAllClaims(jwt);  // Use JwtService
            Map<String, Object> response = new HashMap<>();
            response.put("valid", true);
            response.put("email", claims.getSubject());
            response.put("roles", claims.get("authorities"));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Invalid token
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("valid", false, "message", "Invalid token"));
        }
    }
}
