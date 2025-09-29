package org.example.user.model.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-increment ID
    int id;
    String token;  // Activation code
    LocalDateTime createdAt;  // Creation time
    LocalDateTime expiresAt;  // Expiry time
    LocalDateTime validatedAt;  // Validation time (null if not validated)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)  // Foreign key to Doctor (fixed name)
    Doctor user;
}
