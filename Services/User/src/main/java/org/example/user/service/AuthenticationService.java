package org.example.user.service;

import jakarta.transaction.Transactional;
import org.example.user.model.dtos.AuthenticationResponse;
import org.example.user.model.dtos.AuthenticationRequest;
import org.example.user.model.dtos.Crew;
import org.example.user.model.dtos.RegistrationRequest;
import org.example.user.model.entities.Cabinet;
import org.example.user.model.entities.Token;
import org.example.user.model.entities.Doctor;
import org.example.user.model.enums.Role;
import org.example.user.repository.TokenRepository;
import org.example.user.repository.DoctorRepository;
import org.example.user.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
public class AuthenticationService {
      // Repo for roles

    @Autowired
    private PasswordEncoder passwordEncoder;  // Encoder from BeansConfig

    @Autowired
    private DoctorRepository userRepository;  // User repo

    @Autowired
    private TokenRepository tokenRepository;  // Token repo


    @Autowired
    private AuthenticationManager authenticationManager;  // Manager from BeansConfig

    @Autowired
    public JwtService jwtService;  // JWT service (made public for controller access)

    public void register(RegistrationRequest request)  {
        // Find default role
        Role userRole = Role.DOCTOR;

        // Build and save user with hashed password
        Doctor user = Doctor.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(request.getEmail())
                .pwd(passwordEncoder.encode(request.getPwd()))
                .idun(request.getIdun())
                .datenaiss(request.getDatenaiss())  // Fixed typo from datenais
                .locked(false)
                .enable(false)  // Disabled until activation
                .role(userRole)
                .cabinet(request.getCabinet())
                .build();
        userRepository.save(user);

        // Send activation email
        sendValidationEmail(user);
    }
    public void registerCrew(Crew request, Cabinet cabinet)  {
        // Build and save user with hashed password
        Doctor user = Doctor.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(request.getEmail())
                .pwd(passwordEncoder.encode(request.getPwd()))
                .datenaiss(request.getDatenaiss())  // Fixed typo from datenais
                .locked(false)
                .enable(true)  // Disabled until activation
                .role(request.getRole())
                .cabinet(cabinet)
                .build();
        userRepository.save(user);
    }
    public List<Integer> findByCabinetId(String token) {
        Long cabinetId = jwtService.extractCabinetId(token);
        return userRepository.findByCabinetId(cabinetId);
    }
    public List<Crew> findCrewByCabinetId(String token) {
        Long cabinetId = jwtService.extractCabinetId(token);
        return userRepository.findCrewByCabinetId(cabinetId).stream().map(this::mapToCrew).toList();
    }
    private void sendValidationEmail(Doctor user)  {
        // Generate token and send email
        String newToken = generateAndSaveActivationToken(user);
       // mailingService.sendEmail(user, "Validation", newToken);  // Assume MailingService is implemented
    }

    private String generateAndSaveActivationToken(Doctor user) {
        // Generate 6-digit token
        String generatedToken = generateActivationToken(6);
        // Build and save token entity
        Token token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(30))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationToken(int length) {
        // Generate random numeric token
        String chars = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(chars.length());
            codeBuilder.append(chars.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Authenticate credentials
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPwd())
        );

        // Prepare claims and generate JWT
        var claims = new HashMap<String, Object>();
        var user = (Doctor) auth.getPrincipal();
        System.out.println("Cabinet info: " + user.getCabinet());
        claims.put("cabinet", user.getCabinet());
        System.out.println("Authenticated user: " + user.getEmail());
        claims.put("nom", user.getNom());
        var jwtToken = jwtService.generateToken(claims, user);

        // Build response
        return AuthenticationResponse.builder().token(jwtToken).message("Authentication successful").build();
    }
    public void lockUnlockUser(int id) {
        // Lock or unlock user account
        Doctor user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setLocked(!user.isLocked());
        userRepository.save(user);
    }
    public void deleteUser(int id) {
        // Delete user by ID
        if (!userRepository.existsById(id)) {
            throw new UsernameNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }
    @Transactional
    public boolean activateAccount(String token)  {
        // Find token
        Token savedToken = tokenRepository.findByToken(token);
        if (savedToken == null) {
            throw new RuntimeException("Invalid token");  // Fault fix: Handle null token
        }

        // Check expiration
        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Token expired; new token sent");
        }

        // Enable user
        Doctor user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnable(true);
        userRepository.save(user);

        // Update token validation time
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
        return true;
    }

    public Doctor getCurrentUser(String token) {
        // Extract email from token and find user
        return userRepository.findByEmail(jwtService.extractUsername(token));
    }

    public String getRole(String auth) {
        // Extract role from user's roles
        auth = auth.replace("Bearer ", "");
        Doctor user = userRepository.findByEmail(jwtService.extractUsername(auth));
        if (user == null ) {
            throw new RuntimeException("User or role not found");  // Fault fix
        }
        return user.getRole().toString();  // Assume first role
    }
    public Crew mapToCrew(Doctor doctor) {
        if (doctor == null) {
            throw new IllegalArgumentException("Doctor cannot be null");
        }
        return Crew.builder()
                .id(doctor.getId())
                .nom(doctor.getNom())
                .prenom(doctor.getPrenom())
                .email(doctor.getEmail())
                .datenaiss(doctor.getDatenaiss())
                .role(doctor.getRole())
                .locked(doctor.isLocked())
                .build();
    }
}
