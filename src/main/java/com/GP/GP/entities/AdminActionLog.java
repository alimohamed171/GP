package com.GP.GP.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class AdminActionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String actionType; // e.g., DELETE, UPDATE
    private String entityName;
    private String entityId;
    private String performedBy; // Admin username or ID
    private String description;

    private LocalDateTime timestamp = LocalDateTime.now();

    // Getters and setters...
}
